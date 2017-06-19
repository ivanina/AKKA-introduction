package p1.IoT;


import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class IotSupervisor  extends AbstractActor{
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public static Props props(){
        return Props.create(IotSupervisor.class);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .build();
    }


    @Override
    public void postStop() throws Exception {
        log.info("IotSupervisor | STOPPED");
        super.postStop();
    }

    @Override
    public void preStart() throws Exception {
        log.info("IotSupervisor | STARTED");
        super.preStart();
    }
}
