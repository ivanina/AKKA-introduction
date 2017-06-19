package p1;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class PrintDemo extends AbstractActor  {

    String msg;

    public PrintDemo(String msg) {
        this.msg = msg;
    }

    public PrintDemo() {
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("part-1-demo");

        ActorRef firstRef = system.actorOf(
                Props.create(
                        PrintCustomActorRefActor.class,
                        ">> Props: message from first-actor <<"
                ),
                "first-actor"
        );
        System.out.println("First: "+firstRef + " ( send to marker 'printit-1' message 'message from first-actor')");

        firstRef.tell("printit-1", ActorRef.noSender());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("printit-2", p -> {
                    System.out.println("First: Receive from second (or other) to 'printit-2' marker message: "+msg);
                })
                .build();
    }
}
