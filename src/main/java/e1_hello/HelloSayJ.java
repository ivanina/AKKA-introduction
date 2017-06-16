package e1_hello;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class HelloSayJ {
    ActorSystem system;
    ActorRef hello;

    public HelloSayJ() {
        system = ActorSystem.create("actor-demo-java");
        hello = system.actorOf(Props.create(Hello.class));
    }

    public void shutdown() {
        system.terminate();
    }


    private static class Hello extends UntypedActor{
        @Override
        public void onReceive(Object message) throws Throwable {
            System.out.println("Received message: '"+message+"'. Hi, "+message);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        HelloSayJ handler = new HelloSayJ();
        handler.hello.tell("John Smith",ActorRef.noSender());
        Thread.sleep(1000);
        handler.shutdown();
    }
}
