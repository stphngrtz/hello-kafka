package de.stphngrtz.hellokafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Consumer1 {

    public static final String TOPIC = "test1";

    public static void main(String[] args) {
        ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(new ConsumerConfig(getProperties()));
        Map<String, Integer> map = new HashMap<>();
        map.put(TOPIC, 1);
        Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumerConnector.createMessageStreams(map);

        System.out.println("consuming...");
        ConsumerIterator<byte[], byte[]> it = messageStreams.get(TOPIC).get(0).iterator();
        while (it.hasNext()) {
            try {
                MyDTO myDTO = MyDTO.fromJSON(new String(it.next().message()));
                System.out.println(Thread.currentThread().getName() + " consumed: " + myDTO);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("shutting down consumer");
        consumerConnector.shutdown();
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("zookeeper.connect", "localhost:2181");
        properties.put("group.id", "test_group");
        properties.put("zookeeper.session.timeout.ms", "400000");
        properties.put("zookeeper.sync.time.ms", "200");
        properties.put("auto.commit.interval.ms", "1000");
        return properties;
    }
}
