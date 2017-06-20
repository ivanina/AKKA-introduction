package p3_4;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.HashMap;
import java.util.Map;

public class DeviceManager  extends AbstractActor{

    private  final LoggingAdapter log = Logging.getLogger(context().system(),this);

    final Map<ActorRef,String> actorToGroupId  = new HashMap<>();
    final Map<String,ActorRef> groupIdToActor  = new HashMap<>();

    public static Props props(){
        return Props.create(DeviceManager.class);
    }

    //--------------------

    public static final class RequestTrackDevice{
        public final String groupId;
        public final String deviceId;

        public RequestTrackDevice(String groupId, String deviceId) {
            this.groupId = groupId;
            this.deviceId = deviceId;
        }
    }


    public static final class DeviceRegistered{
    }

    //--------------------

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(RequestTrackDevice.class,this::onTrackDevice)
                .build();
    }

    public void onTrackDevice(RequestTrackDevice trackMsg){
        ActorRef ref = groupIdToActor.get( trackMsg.groupId );
        if(ref != null){
            // TODO
        }else {
            // TODO
        }
    }


    @Override
    public void preStart() throws Exception {
        log.info("DeviceManager  started");
        super.preStart();
    }

    @Override
    public void postStop() throws Exception {
        log.info("DeviceManager  started");
        super.postStop();
    }
}
