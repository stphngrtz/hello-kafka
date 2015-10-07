# hello-kafka

Basiert auf kafka_2.10-0.8.2.2.tgz (einfach entpacken und in gewünschtes Verzeichnis verschieben)

## Zookeeper starten
./kafka_2.10-0.8.2.2/bin/zookeeper-server-start.sh config/zookeeper.properties

## Kafka starten
./kafka_2.10-0.8.2.2/bin/kafka-server-start.sh config/server.properties

## Topic erstellen
./kafka_2.10-0.8.2.2/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

## Consumer
./kafka_2.10-0.8.2.2/bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test --from-beginning

## Producer
./kafka_2.10-0.8.2.2/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test

## Offene Punkte
* Aufteilen von Consumer und Producer auf unterschiedliche Anwendungen
  * Command als Producer
  * Services als Consumer (<artifactId>kafka-clients</artifactId>, http://mvnrepository.com/artifact/org.apache.kafka/kafka-clients/0.8.2.2)
* Consumen aus mehreren Threads funktioniert noch nicht

https://cwiki.apache.org/confluence/display/KAFKA/Consumer+Group+Example