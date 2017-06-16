package e1_hello

import akka.actor.{Actor, ActorSystem, Props}


object HelloSay extends App {

  val system = ActorSystem("actor-demo-scala-2")
  val hello = system.actorOf(Props[Hello])
  hello ! "John"
  Thread.sleep(1000)
  //system shutdown

  class Hello extends Actor {
    def receive = {
      case name: String => println(s"Hello $name")
    }
  }

}
