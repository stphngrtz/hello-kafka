# hello-kafka

Basiert auf kafka_2.10-0.8.2.2.tgz

cd kafka_2.10-0.8.2.2

Zookeeper starten
`./bin/zookeeper-server-start.sh config/zookeeper.properties`

Kafka starten
`./bin/kafka-server-start.sh config/server.properties`

Topic erstellen
`./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test`

Topcis auflisten
`./bin/kafka-topics.sh --list --zookeeper localhost:2181`

Consumer
`./bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test --from-beginning`

Producer
`./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test`