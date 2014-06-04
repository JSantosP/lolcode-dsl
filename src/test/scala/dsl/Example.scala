package dsl

object Example extends App {
  
  import common._
  import LolCode._

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

  run(myProgram)
}