package demo.producer;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient
public interface DemoProducer {

    @Topic("demo-outbound-topic")
    void sendOutbound(String payload);
}
