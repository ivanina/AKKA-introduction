package p1;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class PrintCustomActorRefActor extends AbstractActor {
    String msg;

    public PrintCustomActorRefActor(String message) {
        this.msg = message;
    }

    public PrintCustomActorRefActor() {
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("printit-1", p -> {
                    //ActorRef secondRef = getContext().actorOf(Props.empty(), "second-actor");
                    ActorRef secondRef = getContext().actorOf(Props.create(
                            PrintDemo.class,
                            ">> Props: second-actor <<"
                            ),
                            "second-actor");

                    System.out.println("Second: " + secondRef + "( the marker 'printit-1' receive message: " + msg + ")");

                    secondRef.tell("printit-2", ActorRef.noSender());

                    ActorSystem system = ActorSystem.create("part-1-demo");
                    ActorRef firstRef = system.actorOf(
                            Props.create(
                                    PrintDemo.class,
                                    ">> Props: message from second-actor <<"
                            ),
                            "first-second-actor"
                    );
                    firstRef.tell("printit-2", ActorRef.noSender());
                })
                .build();
    }
}
