package p2.IoT;


import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.Optional;

public class Device extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().system(),this);

    final String groupId;
    final String deviceId;

    Optional<Double> lastTemperatureReading = Optional.empty();

    public Device(String groupId, String deviceId) {
        this.groupId = groupId;
        this.deviceId = deviceId;
    }

    public static Props props(String groupId, String deviceId){
        return Props.create(Device.class,groupId,deviceId);
    }

    //---------------

    public static final class ReadTemperature{
        public long requestId;

        public ReadTemperature(long requestId) {
            this.requestId = requestId;
        }
    }



    public static final class RespondTemperature{
        public long requestId;
        public Optional<Double> value;

        public RespondTemperature(long requestId, Optional<Double> value) {
            this.requestId = requestId;
            this.value = value;
        }
    }

    //---------------


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ReadTemperature.class, p -> {
                    getSender().tell(new RespondTemperature(p.requestId,lastTemperatureReading), getSelf());
                }).build();
    }

    @Override
    public void preStart() throws Exception {
        log.info("Device actor {}-{} started",groupId,deviceId);
        super.preStart();
    }

    @Override
    public void postStop() throws Exception {
        log.info("Device actor {}-{} Stopped",groupId,deviceId);
        super.postStop();
    }
}
