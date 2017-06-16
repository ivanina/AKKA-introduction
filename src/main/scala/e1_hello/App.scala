package e1_hello

import akka.actor.{ActorSystem, Props}
import e1_hello.HelloSay.{Hello, hello}


object App {
  def main(args: Array[String]): Unit = {
    print("Hi")
  }
}
