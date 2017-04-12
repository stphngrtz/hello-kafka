package de.stphngrtz.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static spark.Spark.get;
import static spark.Spark.post;

public class Producer {

    private static final String TOPIC = "test2";
    private static final Logger log = LoggerFactory.getLogger(Producer.class);

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:32768");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 100);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        get("/", (req, res) -> "Kafka Producer");

        get("/:amount", (req, res) -> {
            /*                                                                                                       */ long startProducer = System.currentTimeMillis();
            KafkaProducer<String, String> producer = new KafkaProducer<>(props);
            /*                                                                                                       */ long stopProducer= System.currentTimeMillis();

            int amount = Integer.valueOf(req.params(":amount"));

            /*                                                                                                       */ long startSend = System.currentTimeMillis();
            for (int i=1; i <= amount; i++) {
                producer.send(new ProducerRecord<>(TOPIC, String.valueOf(i), "{\"key\":\"" + i + "\", \"message\":\"Message #" + i + "\"}"));

                if (amount % (amount/10) == 0)
                    log.debug("sent {}/{}", i, amount);
            }
            /*                                                                                                       */ long stopSend = System.currentTimeMillis();

            /*                                                                                                       */ long startClose = System.currentTimeMillis();
            producer.close();
            /*                                                                                                       */ long stopClose = System.currentTimeMillis();

            return String.format("Done! (%d messages, %d ms (create producer), %d ms (send messages), %d ms (close producer))" + System.lineSeparator(), amount, stopProducer - startProducer, stopSend - startSend, stopClose - startClose);
        });

        post("/", (req, res) -> {
            KafkaProducer<String, String> producer = new KafkaProducer<>(props);
            producer.send(new ProducerRecord<>(TOPIC, req.body()));
            producer.close();
            return "";
        });
    }
}
