package p3_4;


import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DeviceGroup extends AbstractActor {
    private  final LoggingAdapter log = Logging.getLogger(context().system(),this);

    final String groupId;

    public DeviceGroup(String groupId) {
        this.groupId = groupId;
    }

    final Map<ActorRef, String> actorToDeviceId = new HashMap<>();
    final Map<String, ActorRef> deviceIdToActor = new HashMap<>();

    public static Props props(String groupId){
        return Props.create(DeviceGroup.class,groupId);
    }

    //-------------

    // as marker
    public static final class RequestDeviceList{
        final long requestId;

        public RequestDeviceList(long requestId) {
            this.requestId = requestId;
        }
    }

    //get state
    public static final class ReplyDeviceList {
        final long requestId;
        final Set<String> ids;

        public ReplyDeviceList(long requestId, Set<String> ids) {
            this.requestId = requestId;
            this.ids = ids;
        }
    }

    //-------------


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(RequestDeviceList.class, p -> {
                    getSender().tell(
                            new ReplyDeviceList(
                                    p.requestId,
                                    deviceIdToActor.keySet()),
                            getSelf()
                    );
                })
                .build();
    }


    @Override
    public void preStart() throws Exception {
        log.info("DeviceGroup  started");
        super.preStart();
    }

    @Override
    public void postStop() throws Exception {
        log.info("DeviceGroup  started");
        super.postStop();
    }
}
