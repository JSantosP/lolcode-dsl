package dsl

import common._

trait statements {

  /** Import a library into current program*/
  case class ImportStatement(library: Library) extends ConsoleStatement{
    def execute(implicit context: Program) = 
      (context.copy(imports=context.imports + library),s"LIBRARY $library WOZ SUCCSFLLY IMPORTED!")
  }

  /** Don't stare at it, it's just a file...*/
  class File(val name: String)

  /** Open a file and register it in program's environment*/
  case class OpenFileStatement(file: File) extends ConsoleStatement{
    def execute(implicit context: Program) = {
      if(context.imports.contains(STDIO)) 
        (context.copy(env=context.env + ("FILE" -> file)),s"FILE ${file.name} WOZ OPENED")
      else {
        (context,s"ERROR: STDIO HAZ NT BIN IMPORTED. CUDN'T OPEN ${file.name}")
      } 
    }
  }

  /** Print a message to stdout or stderr*/
  case class PrintStatement(message: String,visible:Boolean) extends ConsoleStatement{
    def execute(implicit context: Program) = {
      if (!visible) System.err.println(message) else println(message)
      (context,"I ALREADY PRINTD DIS,DIDNT I?")
    }
  }

  /** Print file contents to stdout */
  case class PrintFileStatement(visible: Boolean) extends ConsoleStatement{
    def execute(implicit context: Program) = {
      val (status,msg) = 
        context.env.get("FILE") match {
          case Some(file:File) => 
            try {
              (context,io.Source.fromFile(getClass.getResource(s"/${file.name}").getFile).mkString  )
            } catch {
              case _:Throwable => 
                (context.copy(exceptionThrown=true),"ERROR! FILE WOZ NT FOUND")
            }
            
          case _ => 
            (context,"ERROR! NT FILE WOZ DEFINED")
        }
      PrintStatement(msg,visible).execute
      (status, "PRINTD FILE (I THINK)")
    }
  }

  /** If there's no exception thrown before, executes 'A' statement, else execute a possible 'B' statement*/
  class ConditionalExecution(
    val okStatement: Statement[_], 
    val exceptionCondition: Option[Statement[_]] = None) extends ConsoleStatement{
    def O_NOES(s: Statement[_]) = new ConditionalExecution(okStatement,Some(s))
    def execute(implicit context: Program) = 
      if (!context.exceptionThrown) (okStatement.execute._1,"AWSUM THKX")
      else if (exceptionCondition.isDefined) (exceptionCondition.get.execute._1, "O NOES")
      else (context,"DID NOTHING")
  }
}
