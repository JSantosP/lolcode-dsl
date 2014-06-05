# Scala LolCode DSL

[LOLCODE] is an esoteric programming language inspired by lolspeak, the language expressed in examples of the lolcat Internet meme.The language was created in 2007 by Adam Lindsay, researcher at the Computing Department of Lancaster University.

These short snippets show how Scala empowers you to create new embedded domain specific languages with its powerfull features.

This short DSL allows you to write lolcode with this appearence:
```scala
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
```
It's not exactly the same syntax, but it works for taking account of provided mechanisms to do so.
If you want to run this code (yes,thanks!) you can use the reactive interpreter like this
```scala
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

interpreter ! myProgram
```

If you want to have a look you can clone this project with
```
git clone https://github.com/JSantosP/lolcode-dsl.git
```

If you want to see whether LolCode works reading a file (open your seat belt!) you can check it by executing
```
sbt run
```

[LOLCODE]:http://en.wikipedia.org/wiki/LOLCODE
