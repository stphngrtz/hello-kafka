package de.stphngrtz.hellokafka;

import kafka.admin.AdminUtils;
import kafka.server.KafkaConfig;
import kafka.server.KafkaServerStartable;
import org.I0Itec.zkclient.ZkClient;

import java.io.IOException;
import java.util.Properties;

public class KafkaLocal {

    private final String zookeeper = "localhost:2181";

    private final KafkaServerStartable kafkaServerStartable;
    private boolean isStarted = false;

    public KafkaLocal() throws IOException {
        Properties properties = new Properties();
        // properties.load(this.getClass().getResourceAsStream("./kafka_2.10-0.8.2.2/config/server.properties"));
        properties.setProperty("broker.id", "0");
        properties.setProperty("port", "9092");
        properties.setProperty("num.network.threads", "3");
        properties.setProperty("num.io.threads", "8");
        properties.setProperty("socket.send.buffer.bytes", "102400");
        properties.setProperty("socket.receive.buffer.bytes", "102400");
        properties.setProperty("socket.request.max.bytes", "104857600");
        properties.setProperty("log.dirs", "/tmp/kafka-logs");
        properties.setProperty("num.partitions", "1");
        properties.setProperty("num.recovery.threads.per.data.dir", "1");
        properties.setProperty("log.retention.hours", "168");
        properties.setProperty("log.segment.bytes", "1073741824");
        properties.setProperty("log.retention.check.interval.ms", "300000");
        properties.setProperty("log.cleaner.enable", "false");
        properties.setProperty("zookeeper.connect", zookeeper);
        properties.setProperty("zookeeper.connection.timeout.ms", "6000");

        KafkaConfig kafkaConfig = new KafkaConfig(properties);
        kafkaServerStartable = new KafkaServerStartable(kafkaConfig);
    }

    public void start() {
        if (!isStarted) {
            System.out.println("starting kafka...");
            kafkaServerStartable.startup();
            System.out.println("done!");
            isStarted = true;
        }
    }

    public void stop() {
        if (isStarted) {
            System.out.println("stopping kafka...");
            kafkaServerStartable.shutdown();
            System.out.println("done!");
            isStarted = false;
        }
    }

    public void createTopic(String topic, int partitions, int replicationFactor) {
        AdminUtils.createTopic(new ZkClient(zookeeper), topic, partitions, replicationFactor, new Properties());

        if (!AdminUtils.topicExists(new ZkClient(zookeeper), topic))
            System.out.println("topic '" + topic + "' not created!");
        else
            System.out.println("topic '" + topic + "' created!");
    }
}
