package dsl

import common._

trait statements {

	/** Import a library into current program*/
	case class ImportStatement(library: Library) extends Statement{
		def execute(implicit context: Program): Program = 
			context.copy(imports=context.imports + library)
	}

	/** Don't stare at it, it's just a file...*/
	class File(val name: String)

	/** Open a file and register it in program's environment*/
	case class OpenFileStatement(file: File) extends Statement {
		def execute(implicit context: Program): Program = {
			if(context.imports.contains(STDIO)) 
				context.copy(env=context.env + ("FILE" -> file))
			else {
				println("STDIO HAZ NT BIN IMPORTED")
				context
			} 
		}
	}

	/** Print a message to stdout or stderr*/
	case class PrintStatement(message: String,visible:Boolean) extends Statement {
		def execute(implicit context: Program): Program = {
			if (!visible) System.err.println(message) else println(message)
			context
		}
	}

	/** Print file contents to stdout */
	case class PrintFileStatement(visible: Boolean) extends Statement {
		def execute(implicit context: Program): Program = {
			val (status,msg) = 
				context.env.get("FILE") match {
					case Some(file:File) => 
						try {
							(context,io.Source.fromFile(getClass.getResource(s"/${file.name}").toString.drop("file:".size)).mkString	)
						} catch {
							case _:Throwable => 
								(context.copy(exceptionThrown=true),"ERROR! FILE WOZ NT FOUND")
						}
						
					case _ => 
						(context,"ERROR! NT FILE WOZ DEFINED")
				}
			PrintStatement(msg,visible).execute
			status
		}
	}

	/** If there's no exception thrown before, executes 'A' statement, else execute a possible 'B' statement*/
	class ConditionalExecution(
		val okStatement: Statement, 
		val exceptionCondition: Option[Statement] = None) extends Statement{
		def O_NOES(s: Statement) = new ConditionalExecution(okStatement,Some(s))
		def execute(implicit context: Program): Program = 
			if (!context.exceptionThrown) okStatement.execute
			else if (exceptionCondition.isDefined) exceptionCondition.get.execute
			else context
	}
}