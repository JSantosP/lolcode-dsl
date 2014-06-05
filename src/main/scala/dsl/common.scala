package dsl

/**
  * This dummy implementation of Library represents 
  * a set of functionality to be imported
  */
trait Library{
  val name: String
}

/** 
  * A program is mainly composed by its environment, a sequence of statements and
  * a set of imported libraries.
  */
case class Program (
  val env: Map[String, Any],
  val statements: Seq[Statement[_]],
  val imports: Set[Library] = Set(), 
  val isSyntaxOk: Boolean = false,
  val exceptionThrown: Boolean = false){
}

/** 
  * A Statement acts just like a state transformer when it's executed.
  * It returns the updated program and some output.
  */
trait Statement[Output]{
  def execute(implicit context: Program):(Program,Output)
}

/** A special kind of statement that always return a string as output */
trait ConsoleStatement extends Statement[String]

object common {

  // Messages

  case class Finished(program: Program)
  case class WrongSyntax(program: Program)

  // Libraries

  case object STDIO extends Library { val name = "stdio" }

  // Helpers

  implicit def emptyStatement(u: Unit): ConsoleStatement = new ConsoleStatement{
    def execute(implicit context: Program) = (context,"DUNNO WAT TO DO")
  }

}