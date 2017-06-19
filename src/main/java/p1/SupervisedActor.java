package p1;

import akka.actor.AbstractActor;

public class SupervisedActor  extends AbstractActor{

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("fail", p -> {
                    System.out.println("supervised (child) actor fails now");
                    throw new Exception("I failed!");
                })
                .build();
    }

    @Override
    public void preStart() throws Exception {
        System.out.println("SupervisedActor (child) | STARTED");
        super.preStart();
    }

    @Override
    public void postStop() throws Exception {
        System.out.println("SupervisedActor (child) | STOPPED");
        super.postStop();
    }

}
