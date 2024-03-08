package demo.consumer;

import demo.event.DemoInboundEvent;
import demo.service.DemoService;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@KafkaListener(groupId = "demo-group=id")
public class DemoConsumer {

    @Inject
    private DemoService demoService;

    @Topic("demo-inbound-topic")
    public void receive(DemoInboundEvent demoInboundEvent) {
        log.info("Received message with id: " + demoInboundEvent.id());
        try {
            demoService.process(demoInboundEvent);
        } catch (Exception e) {
            log.error("Error processing message: " + e.getMessage());
        }
    }
}
