package de.stphngrtz.hellokafka;

import kafka.admin.AdminUtils;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class Server1 {

    public static void main(String[] args) {
        ZookeeperLocal zookeeperLocal = null;
        try {
            zookeeperLocal = new ZookeeperLocal();
            zookeeperLocal.start();

            KafkaLocal kafkaLocal = new KafkaLocal();
            kafkaLocal.start();

            kafkaLocal.createTopic("test1", 1, 1);
            kafkaLocal.createTopic("test2", 1, 1);

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("q to quit: ");
            try {
                String s = br.readLine();
                if ("q".equals(s)) {
                    kafkaLocal.stop();
                    zookeeperLocal.stop();
                }
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        } catch (IOException | QuorumPeerConfig.ConfigException e) {
            System.err.println("server startup failed");
            e.printStackTrace(System.err);
        }
    }
}
