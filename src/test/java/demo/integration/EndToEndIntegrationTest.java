package demo.integration;

import java.util.Map;
import java.util.UUID;

import demo.event.DemoInboundEvent;
import dev.lydtech.component.framework.mapper.JsonMapper;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;
import net.mguenther.kafka.junit.EmbeddedKafkaCluster;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static net.mguenther.kafka.junit.EmbeddedKafkaCluster.provisionWith;
import static net.mguenther.kafka.junit.EmbeddedKafkaClusterConfig.defaultClusterConfig;
import static net.mguenther.kafka.junit.ObserveKeyValues.on;
import static net.mguenther.kafka.junit.SendValues.to;

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EndToEndIntegrationTest implements TestPropertyProvider {

    private final static String DEMO_INBOUND_TEST_TOPIC = "demo-inbound-topic";
    private final static String DEMO_OUTBOUND_TEST_TOPIC = "demo-outbound-topic";
    private static EmbeddedKafkaCluster kafka;

    static {
        kafka = provisionWith(defaultClusterConfig());
        kafka.start();
    }

    @Override
    public @NonNull Map<String, String> getProperties() {
        return Map.of(
            "kafka.bootstrap.servers", kafka.getBrokerList()
        );
    }

    @BeforeAll
    public void setupOnce() {
        KafkaTestUtils.initialize(kafka.getBrokerList()).waitForApplicationConsumer(DEMO_INBOUND_TEST_TOPIC);
    }

    @AfterAll
    public static void tearDownOnce() {
        kafka.stop();
    }

    @Test
    public void testSuccess() throws Exception {
        int totalMessages = 5;
        for (int i = 0; i < totalMessages; i++) {
            String id = UUID.randomUUID().toString();
            String payload = "payload-"+UUID.randomUUID();
            String event = JsonMapper.writeToJson(new DemoInboundEvent(id, payload));
            kafka.send(to(DEMO_INBOUND_TEST_TOPIC, event));
        }
        kafka.observe(on(DEMO_OUTBOUND_TEST_TOPIC, totalMessages));
    }
}
