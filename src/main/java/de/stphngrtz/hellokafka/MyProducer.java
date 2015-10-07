package de.stphngrtz.hellokafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

import java.math.BigDecimal;

public class MyProducer implements Runnable {

    private final Producer<String, byte[]> producer;
    private final String topic;

    public MyProducer(Producer<String, byte[]> producer, String topic) {
        this.producer = producer;
        this.topic = topic;
    }

    @Override
    public void run() {
        System.out.println("producing...");
        for (int i = 0; i < 5; i++) {
            MyDTO myDTO = new MyDTO();
            myDTO.id = i;
            myDTO.name = "DTO " + i;
            myDTO.value = BigDecimal.valueOf((i+1)*100);
            producer.send(new KeyedMessage<>(topic, new MyDTOEncoder().toBytes(myDTO)));
            System.out.println(Thread.currentThread().getName() +" produced: "+ myDTO);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("shutting down producer");
    }
}
