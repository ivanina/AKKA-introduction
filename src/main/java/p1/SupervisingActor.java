package p1;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class SupervisingActor  extends AbstractActor{

    @Override
    public Receive createReceive() {
        final ActorRef child = getSystem().actorOf( Props.create(SupervisedActor.class));
        return receiveBuilder()
                .matchEquals("child-fail", p -> {
                    child.tell("fail",getSelf());
                })
                .build();
    }

    public static void main(String[] args) {
        ActorRef supervising = getSystem().actorOf( Props.create(SupervisingActor.class) );
        supervising.tell("child-fail",ActorRef.noSender());
    }

    static ActorSystem getSystem(){
        return ActorSystem.create("supervised-supervising");
    }

    @Override
    public void preStart() throws Exception {
        System.out.println("SupervisingActor (main) | STARTED");
        super.preStart();
    }

    @Override
    public void postStop() throws Exception {
        System.out.println("SupervisingActor (main) | STOPPED");
        super.postStop();
    }
}
