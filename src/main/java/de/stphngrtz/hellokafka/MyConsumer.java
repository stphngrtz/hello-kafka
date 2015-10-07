package de.stphngrtz.hellokafka;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

public class MyConsumer implements Runnable {

    private final KafkaStream<byte[], byte[]> stream;

    public MyConsumer(KafkaStream<byte[], byte[]> stream) {
        this.stream = stream;
    }

    @Override
    public void run() {
        System.out.println("consuming...");
        ConsumerIterator<byte[], byte[]> it = stream.iterator();
        while (it.hasNext()) {
            MyDTO myDTO = new MyDTODecoder().fromBytes(it.next().message());
            System.out.println(Thread.currentThread().getName() +" consumed: "+ myDTO);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("shutting down consumer");
    }
}
