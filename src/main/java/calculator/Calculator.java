package calculator;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import scala.Int;

public class Calculator extends AbstractActor {

    private final ActorRef outbutter;
    private Integer p1Int;
    private Double p1Dbl;

    public Calculator(ActorRef outbutter) {
        this.outbutter = outbutter;
        this.p1Int = 0;
        this.p1Dbl = 0.0;
    }

    static public Props props(ActorRef outbutter){
        return Props.create(Calculator.class, () -> new Calculator(outbutter));
    }

    static public class CalculateInt{
        public final Integer parameterInt;

        public CalculateInt(Integer parameterInt) {
            this.parameterInt = parameterInt;
        }
    }

    static public class CalculateDouble{
        public final Double parameterDouble;

        public CalculateDouble(Double parameterDouble) {
            this.parameterDouble = parameterDouble;
        }
    }

    private Integer calculatorCalculate(){
        return p1Int + p1Dbl.intValue();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(CalculateInt.class, p1Int -> {

                    //to do something very hard
                    System.out.println("(A) Start to-do for INT");
                    Thread.sleep(3000);
                    System.out.println("(A) Finished to-do for INT");
                    //to do finished something very hard

                    this.p1Int = p1Int.parameterInt;
                })
                .match(CalculateDouble.class, p1Dbl -> {
                    this.p1Dbl = p1Dbl.parameterDouble;
                    
                    //to do something very hard
                    System.out.println("(B) Start to-do for Double");
                    Thread.sleep(2000);
                    System.out.println("(B) Finished to-do for Double");
                    //to do finished something very hard
                    Integer result = calculatorCalculate();
                    this.outbutter.tell(new Outputter.Printer(result),self() );
                })
                .build();
    }
}
