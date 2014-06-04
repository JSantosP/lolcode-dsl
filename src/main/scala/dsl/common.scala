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
  val statements: Seq[Statement],
  val imports: Set[Library] = Set(), 
  val isSyntaxOk: Boolean = false,
  val exceptionThrown: Boolean = false){
}

/** A Statement acts just like a state transformer when it's executed*/
trait Statement{
  def execute(implicit context: Program):Program
}

object common {

  def run(program: Program): Program =
    if (!program.isSyntaxOk) program
    else (program /: program.statements)((context, statement) =>
      statement.execute(context))

  // Libraries

  case object STDIO extends Library { val name = "stdio" }

  // Helpers

  implicit def emptyStatement(u: Unit): Statement = new Statement{
    def execute(implicit context: Program):Program = context
  }

}