package de.stphngrtz.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

public class Consumer1 {

    private static final String TOPIC = "test2";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:32768");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "Group_A");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(TOPIC));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            if (records.count() == 0)
                continue;

            records.forEach(record -> System.out.printf("partition=%d, offset=%d, key=%s, value=%s" + System.lineSeparator(), record.partition(), record.offset(), record.key(), record.value()));
            System.out.printf("%d records consumed" + System.lineSeparator(), records.count());
        }
    }
}
