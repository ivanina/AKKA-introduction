package p1;


import akka.actor.AbstractActor;

public class StartStopActor2 extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .build();
    }

    @Override
    public void postStop() throws Exception {
        System.out.println("StartStopActor2 | 'actor-2' | STOPPED");
        super.postStop();
    }

    @Override
    public void preStart() throws Exception {
        System.out.println("StartStopActor2 | 'actor-2' | STARTED");
        super.preStart();
    }
}
