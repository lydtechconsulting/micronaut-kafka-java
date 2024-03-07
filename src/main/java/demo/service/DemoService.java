package demo.service;

import demo.event.DemoInboundEvent;
import demo.event.DemoOutboundEvent;
import demo.producer.DemoProducer;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import static java.util.UUID.randomUUID;

@Slf4j
@Singleton
public class DemoService {

    @Inject
    private  DemoProducer demoProducer;

    public void process(DemoInboundEvent demoInboundEvent) {
        DemoOutboundEvent demoOutboundEvent = new DemoOutboundEvent(randomUUID().toString(), "Processed data: "+demoInboundEvent.data());
        demoProducer.sendOutbound(demoOutboundEvent.data());
        log.info("Sent outbound event for consumed event with id: {}", demoInboundEvent.id());
    }
}
