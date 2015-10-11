package de.stphngrtz.hellokafka;

import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

public class ZookeeperLocal {

    private ZooKeeperServerMain zooKeeperServerMain;
    private ServerConfig serverConfig;
    private boolean isStarted = false;

    public ZookeeperLocal() throws IOException, QuorumPeerConfig.ConfigException {
        Properties properties = new Properties();

        // properties.load(this.getClass().getResourceAsStream("./kafka_2.10-0.8.2.2/config/zookeeper.properties"));
        properties.setProperty("dataDir", "/tmp/zookeeper");
        properties.setProperty("clientPort", "2181");
        properties.setProperty("maxClientCnxns", "0");

        QuorumPeerConfig quorumPeerConfig = new QuorumPeerConfig();
        quorumPeerConfig.parseProperties(properties);

        serverConfig = new ServerConfig();
        serverConfig.readFrom(quorumPeerConfig);

        zooKeeperServerMain = new ZooKeeperServerMain();
    }

    public void start() throws IOException {
        System.out.println("starting zookeeper...");

        new Thread() {
            public void run() {
                try {
                    zooKeeperServerMain.runFromConfig(serverConfig);
                } catch (IOException e) {
                    System.out.println("zookeeper startup failed");
                    e.printStackTrace(System.err);
                }
            }
        }.start();

        System.out.println("done!");
        isStarted = true;
    }

    public void stop() {
        isStarted = false;
    }
}
