package p1;


import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class StartStopActor1 extends AbstractActor{

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("actor-1", p -> {
                    System.out.println("StartStopActor1 | 'actor-1' | receive: "+ p);
                })
                .matchEquals("stop", p -> {
                    System.out.println("StartStopActor1 | 'actor-1' | receive: "+ p);
                    getContext().stop(getSelf());
                })
                .build();
    }

    @Override
    public void preStart() throws Exception {
        System.out.println("StartStopActor1 | 'actor-1' | STARTED");
        getContext().actorOf( Props.create(StartStopActor2.class) );
        super.preStart();
    }

    @Override
    public void postStop() throws Exception {
        System.out.println("StartStopActor1 | 'actor-1' | STOPPED");
        super.postStop();
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("start-stop-demo");
        ActorRef actor1 = system.actorOf(Props.create(StartStopActor1.class));
        actor1.tell("actor-1", ActorRef.noSender());
        actor1.tell("stop",ActorRef.noSender());
    }
}
