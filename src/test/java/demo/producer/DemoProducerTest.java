package demo.producer;

import demo.event.DemoOutboundEvent;
import org.junit.jupiter.api.Test;


public class DemoProducerTest {

    @Test
    void testSendOutbound() {
        DemoProducer demoProducer = new DemoProducer() {
            @Override
            public void sendOutbound(DemoOutboundEvent demoOutboundEvent) {}
        };
        DemoOutboundEvent demoOutboundEvent = new DemoOutboundEvent("1", "Test Data");
        demoProducer.sendOutbound(demoOutboundEvent);
    }
}
