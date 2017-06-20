package p3_4;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class DeviceTest {
    private ActorSystem system;

    @Before
    public void before(){
        this.system = ActorSystem.create("IoT-test");
    }

    @Test
    public void testReplyWithLatestTemperatureReading(){
        TestKit probe = new TestKit(system);
        ActorRef device = system.actorOf( Device.props("group_AA", "device#1") );

        device.tell(new Device.RecordTemperature(1L,24.0), probe.getRef());
        Device.TemperatureRecorded recorded = probe.expectMsgClass(Device.TemperatureRecorded.class);
        assertEquals(1L, recorded.requestId);

        device.tell(new Device.ReadTemperature(2L), probe.getRef());
        Device.RespondTemperature response = probe.expectMsgClass(Device.RespondTemperature.class);
        assertEquals(Optional.of(24.0), response.value);

        device.tell(new Device.RecordTemperature(3L,12.5), probe.getRef());
        Device.TemperatureRecorded recorded2 = probe.expectMsgClass(Device.TemperatureRecorded.class);
        assertEquals(3L, recorded2.requestId);

        device.tell(new Device.ReadTemperature(4L), probe.getRef());
        Device.RespondTemperature response2 = probe.expectMsgClass(Device.RespondTemperature.class);
        assertEquals(Optional.of(12.5), response2.value);
    }
}
