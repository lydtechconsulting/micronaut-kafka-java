package demo.producer;

import demo.event.DemoOutboundEvent;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient
public interface DemoProducer {

    @Topic("demo-outbound-topic")
    void sendOutbound(DemoOutboundEvent demoOutboundEvent);
}
