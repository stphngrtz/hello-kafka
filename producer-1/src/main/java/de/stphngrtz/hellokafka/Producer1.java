package de.stphngrtz.hellokafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;

public class Producer1 {

    public static final String TOPIC1 = "test1";
    public static final String TOPIC2 = "test2";

    public static void main(String[] args) {
        KafkaProducer<String, String> producer = new KafkaProducer<>(getProperties());

        System.out.println("producing...");
        for (int i = 1; i <= 10; i++) {
            MyDTO myDTO = new MyDTO();
            myDTO.id = i;
            myDTO.name = "DTO " + i;
            myDTO.value = new BigDecimal(i).divide(new BigDecimal(100), 3, RoundingMode.HALF_UP);

            try {
                producer.send(new ProducerRecord<>(TOPIC1, myDTO.toJson()));
                producer.send(new ProducerRecord<>(TOPIC2, "sending " + myDTO.id + " to " + TOPIC1));
                System.out.println(Thread.currentThread().getName() + " produced: " + myDTO);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            pause();
        }
        System.out.println("shutting down producer");
        producer.close();
    }

    private static Properties getProperties() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    private static void pause() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
