# hello-kafka
[Kafka](https://kafka.apache.org) is used for building real-time data pipelines and streaming apps. It is horizontally scalable, fault-tolerant, wicked fast, and runs in production in thousands of companies.

## How to?
Just start the docker image and run the producer and/or (multiple) consumer(s).

- GET http://localhost:4567 will do nothing
- GET http://localhost:4567/100 will produce 100 messages
- GET http://localhost:4567/1000 will produce 1000 messages
- POST http://localhost:4567 will produce the body of the request as message

Administration:
- GET http://&lt;dockerhost&gt;:9000 will bring up the Kafka-Manager.

## Docker

- https://hub.docker.com/r/wurstmeister/kafka/
- https://github.com/wurstmeister/kafka-docker

```
docker-compose up -d
docker-compose scale kafka=3
```

**TODO:** Kafka-Manager is not showing any metrics.. don't know why :/
