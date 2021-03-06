package dsl

object LolCode extends statements {

  /**
    * Ussage:
    *   CAN HAS <library:String> ?
    */
  class Can(l: Library){
    def ? : ConsoleStatement = ImportStatement(l)
  }
  object CAN {
    def HAS(library: Library): Can = new Can(library)
  }

  /**
    * Ussage:
    *   PLZ OPEN FILE <fileName:String> ?
    */
  class Plz(f: File){
    def ? : ConsoleStatement = OpenFileStatement(f)
  }
  object PLZ {
    def OPEN(file: File): Plz = new Plz(file)
  }
  object FILE{
    def apply(name: String): File = new File(name)
  }

  /**
    * Ussage:
    *   VISIBLE <msg: String>
    *    VISIBLE FILE
    */
  object VISIBLE{
    def apply(s: String): ConsoleStatement =
      PrintStatement(s,visible=true)
    def FILE: ConsoleStatement = 
      PrintFileStatement(visible=true)
  }

  /**
    * Ussage:
    *   INVISIBLE <error: String>
    */
  object INVISIBLE{
    def apply(msg: String): ConsoleStatement = 
      PrintStatement(msg,visible=false)
    def FILE: ConsoleStatement = 
      PrintFileStatement(visible=false)
  }

  /**
    * Ussage:
    *   AWSUM THX { <okCondition: Statement> } O_NOES { <exceptionCondition: Statement> }
    */
  object AWSUM{
    def THX(okStatement: ConsoleStatement) = new ConditionalExecution(okStatement)
  }

  // Helpers

  object HAI {
    def apply(statements: ConsoleStatement*): Program = 
      Program(Map(),statements)
  }

  implicit class programHelper(p: Program) {
    def KTHXBYE: Program = Program(p.env,p.statements,isSyntaxOk=true)
  }

}