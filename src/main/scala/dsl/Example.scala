package dsl

import akka.actor.{ActorSystem,Actor,Props}

import common._
import LolCode._

object Example extends App with InterpreterActors {

  /** 
    * This program opens the file 'lolcats.txt' 
    * and print its contents in stdout.
    */
  val myProgram =
    HAI (
      CAN HAS STDIO ?,
      PLZ OPEN FILE("LOLCATS.TXT") ?,
      AWSUM THX {
        VISIBLE FILE
      } O_NOES {
        INVISIBLE ("ERROR!")
      }
    ) KTHXBYE

  interpreter ! myProgram

}

/** This trait contains the necessary akka stuff*/
trait InterpreterActors {

  val system = ActorSystem("MyActorSystem")

  /** A dummy logger to print out all traces */
  val logger = system.actorOf(Props(new Actor{
    def receive = {
      case msg => 
        println(msg)
    }
  }))

  /** Lolcode interpreter */
  val interpreter = 
    system.actorOf(Props(new Interpreter(logger)))

}