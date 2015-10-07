package de.stphngrtz.hellokafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static final String TOPIC = "test";

    public static void main(String[] args) {
        ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(new ConsumerConfig(getConsumerProperties()));
        Map<String, Integer> map = new HashMap<>();
        map.put(TOPIC, 2);
        Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumerConnector.createMessageStreams(map);

        //Producer<String, String> producer = new Producer<>(producerConfig);
        //producer.send(new KeyedMessage<>(TOPIC, "Test aus Java :)"));
        Producer<String, byte[]> producer = new Producer<>(new ProducerConfig(getProducerProperties()));

        ExecutorService es = Executors.newFixedThreadPool(5);
        es.submit(new MyConsumer(messageStreams.get(TOPIC).get(0)));
        es.submit(new MyConsumer(messageStreams.get(TOPIC).get(1)));
        es.submit(new MyProducer(producer, TOPIC));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignored) {
        }

        consumerConnector.shutdown();
        es.shutdown();

        try {
            if (!es.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                System.out.println("Timed out waiting for consumer threads to shut down, exiting uncleanly");
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted during shutdown, exiting uncleanly");
        }
    }

    private static Properties getConsumerProperties() {
        Properties properties = new Properties();
        properties.put("zookeeper.connect", "localhost:2181");
        properties.put("group.id", "test_group");
        properties.put("zookeeper.session.timeout.ms", "400000");
        properties.put("zookeeper.sync.time.ms", "200");
        properties.put("auto.commit.interval.ms", "1000");
        return properties;
    }

    private static Properties getProducerProperties() {
        Properties properties = new Properties();
        properties.put("zk.connect", "localhost:2181");
        //properties.put("serializer.class","kafka.serializer.StringEncoder");
        properties.put("metadata.broker.list", "localhost:9092");
        return properties;
    }
}
