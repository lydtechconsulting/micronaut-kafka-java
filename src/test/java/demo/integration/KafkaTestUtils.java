package demo.integration;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ConsumerGroupListing;
import org.apache.kafka.clients.admin.DescribeConsumerGroupsResult;
import org.apache.kafka.clients.admin.ConsumerGroupDescription;
import org.apache.kafka.clients.admin.ListConsumerGroupsResult;
import org.awaitility.Awaitility;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class KafkaTestUtils {

    private static AdminClient adminClient;
    private static KafkaTestUtils kafkaTestUtils;

    private KafkaTestUtils(String bootstrapServers) {
        this.adminClient = AdminClient.create(Map.of(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers
        ));
    }

    public static KafkaTestUtils initialize(String bootstrapServers) {
        if(kafkaTestUtils==null) {
            kafkaTestUtils = new KafkaTestUtils(bootstrapServers);
        }
        return kafkaTestUtils;
    }

    /**
     * Check that the given topic has the expected number of consumers.  This can be used to ensure
     * the application consumer has successfully started consuming from a topic before the test sends
     * in events.
     */
    public static void waitForApplicationConsumer(String topic) {
        Awaitility.await()
            .atMost(30, TimeUnit.SECONDS)
            .until(() -> isConsumerListening(topic));
    }

    private static boolean isConsumerListening(String topic) throws ExecutionException, InterruptedException {
        ListConsumerGroupsResult groupsResult = adminClient.listConsumerGroups();
        Collection<ConsumerGroupListing> groupListings = groupsResult.all().get();

        for (ConsumerGroupListing groupListing : groupListings) {
            DescribeConsumerGroupsResult describeResult = adminClient.describeConsumerGroups(
                    Collections.singletonList(groupListing.groupId())
            );
            ConsumerGroupDescription groupDescription = describeResult.all().get().get(groupListing.groupId());

            boolean isListening = groupDescription.members().stream()
                    .flatMap(member -> member.assignment().topicPartitions().stream())
                    .anyMatch(tp -> tp.topic().equals(topic));

            if (isListening) {
                return true;
            }
        }
        return false;
    }
}
