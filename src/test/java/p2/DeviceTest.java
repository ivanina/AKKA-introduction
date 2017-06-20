package p2;

import akka.testkit.javadsl.TestKit;
import org.junit.*;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import p2.IoT.Device;


import java.util.Optional;

import static org.junit.Assert.*;

public class DeviceTest  {

    private ActorSystem system;

    @Before
    public void before(){
        this.system = ActorSystem.create("IoT-test");
    }

    @Test
    public void testReplyWithEmptyReadingIfNoTemperatureIsKnown() {
        TestKit probe = new TestKit(system);

        ActorRef deviceActor = system.actorOf(Device.props("group", "device"));
        deviceActor.tell(new Device.ReadTemperature(42L), probe.getRef());

        Device.RespondTemperature response = probe.expectMsgClass(Device.RespondTemperature.class);
        assertEquals(42L, response.requestId);
        assertEquals(Optional.empty(), response.value);
    }


}
