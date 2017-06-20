package e1_hello;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class HelloSayLambda {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("actor-demo-java8");
        ActorRef hello = system.actorOf(Props.create(Hello.class));
        hello.tell("Bob", ActorRef.noSender());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) { /* ignore */ }
        system.terminate();
    }

    private static class Hello extends AbstractActor {
        /**
         * Old implementation
         * See to http://doc.akka.io/docs/akka/current/scala/project/migration-guide-2.4.x-2.5.x.html
         */
        /*public Hello() {
            receive(
                    ReceiveBuilder
                            .match( //Non-static method 'match(java.lang.Class<P>, akka.japi.pf.FI.UnitApply<P>)' cannot be referenced from a static context
                                    String.class, s -> System.out.println("Hi " + s))
                            .build());
        }*/

        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .match(String.class, s -> System.out.println("Hi, "+s.toUpperCase()))
                    .build();
        }
    }
}
