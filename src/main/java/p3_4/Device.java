package p3_4;


import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.Optional;

public class Device extends AbstractActor {

    private  final LoggingAdapter log = Logging.getLogger(context().system(),this);

    private String groupId;
    private String deviceId;

    Optional<Double> lastTemperatureReading = Optional.empty();

    public Device(String groupId, String deviceId) {
        this.groupId = groupId;
        this.deviceId = deviceId;
    }

    public static Props props(String groupId, String deviceId){
        return Props.create(Device.class,groupId,deviceId);
    }

    //-----------------

    // recording - it's state
    public final static class RecordTemperature{
        public final long requestId;
        public final double value;

        public RecordTemperature(long requestId, double value) {
            this.requestId = requestId;
            this.value = value;
        }
    }

    // report about recording - it's marker
    public final static class TemperatureRecorded {
        public final long requestId;

        public TemperatureRecorded(long requestId) {
            this.requestId = requestId;
        }
    }


    // requesting temperature - it's marker
    public final static class ReadTemperature {
        public final long requestId;

        public ReadTemperature(long requestId) {
            this.requestId = requestId;
        }
    }

    // responding temperature - it's state
    public final static  class  RespondTemperature {
        public final long requestId;
        public final Optional<Double> value;

        public RespondTemperature(long requestId, Optional<Double> value) {
            this.requestId = requestId;
            this.value = value;
        }
    }


    //------------------

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(RecordTemperature.class, r -> {
                    lastTemperatureReading = Optional.of(r.value);
                    log.info("Device {} - {}. Temperature updated - set new value: {}",
                            groupId,deviceId,lastTemperatureReading.get());
                    getSender().tell(
                            new TemperatureRecorded(r.requestId),
                            getSelf()
                    );
                })
                .match(ReadTemperature.class, p -> {
                    log.info("Device {} - {}. Asked the temperature. Responded value: {}",
                            groupId,deviceId,lastTemperatureReading.get());
                    getSender().tell(
                            new RespondTemperature(p.requestId,lastTemperatureReading),
                            getSelf()
                    );
                })
                .build();
    }

    @Override
    public void preStart() throws Exception {
        log.info("Device {} - {} started",groupId,deviceId);
        super.preStart();
    }

    @Override
    public void postStop() throws Exception {
        log.info("Device {} - {} stopped",groupId,deviceId);
        super.postStop();
    }
}
