package dsl

import akka.actor.{Actor,ActorRef}
import common._

/** 
  * An Interpreter can receive a program and handles it
  * by executing each of its statements. At the same time
  * it sends to the logger actor all posible output.
  */
class Interpreter(logger: ActorRef) extends Actor {

  def receive = {

    case program: Program => 
      if (!program.isSyntaxOk) 
      	sender ! WrongSyntax(program)
      else program.statements.toList match {
      	case (head::tail) => 
      		val (updatedProgram,output) = head.execute(
      			program.copy(statements=program.statements.tail))
      		logger ! output
      		self ! updatedProgram
      	case _ => 
      		logger ! Finished(program)
      }

  }

}