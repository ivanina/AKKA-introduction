package calculator;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.io.IOException;

public class Runner {
    public static void main(String[] args) throws IOException {
        final ActorSystem system = ActorSystem.create("calculator-rest");

        final ActorRef printerActor = system.actorOf(Outputter.props(),"printer");
        final ActorRef calculateServiceActor = system.actorOf(Calculator.props(printerActor), "calService");


        calculateServiceActor.tell( new Calculator.CalculateInt(11 ), ActorRef.noSender());

        calculateServiceActor.tell( new Calculator.CalculateDouble(11.12 ), ActorRef.noSender());
    }
}
