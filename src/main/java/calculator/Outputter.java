package calculator;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class Outputter extends AbstractActor {

    public static Props props(){
        return Props.create(Outputter.class, () -> new Outputter());
    }

    static public class Printer{
        public final Integer intVal;

        public Printer(Integer intVal) {
            this.intVal = intVal;
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Printer.class, val -> {
                    System.out.println("Result: "+ val.intVal);
                })
                .build();
    }
}
