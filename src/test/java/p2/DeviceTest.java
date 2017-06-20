package p2;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import org.junit.Before;
import org.junit.Test;
import p2.IoT.Device;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

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

    @Test
    public void testReplyWithLatestTemperatureReading(){
        TestKit probe = new TestKit(system);
        ActorRef deviceActor = system.actorOf(Device.props("group-A", "device-1"));

        // set temperature
        deviceActor.tell( new Device.RecordTemperature(1L,24.5), probe.getRef() );
        Device.TemperatureRecorded temperatureRecorded = probe.expectMsgClass(Device.TemperatureRecorded.class);
        assertEquals( 1L, temperatureRecorded.requestId);

        // get temperature
        deviceActor.tell( new Device.ReadTemperature(2L), probe.getRef() );
        Device.RespondTemperature respond = probe.expectMsgClass(Device.RespondTemperature.class);
        assertEquals(2L, respond.requestId);
        assertEquals(Optional.of(24.5),respond.value);

        // set new temperature
        deviceActor.tell( new Device.RecordTemperature(3L,33.3), probe.getRef() );
        Device.TemperatureRecorded temperatureRecorded2 = probe.expectMsgClass(Device.TemperatureRecorded.class);
        assertEquals( 3L, temperatureRecorded2.requestId);

        // requested temperature (updated)
        deviceActor.tell( new Device.ReadTemperature(4L), probe.getRef() );
        Device.RespondTemperature respond2 = probe.expectMsgClass(Device.RespondTemperature.class);
        assertEquals(4L, respond2.requestId);
        assertEquals(Optional.of(33.3),respond2.value);
    }


}
