# Micronaut & Java Demo integrating with Kafka

Demo Micronaut application written in Java that integrates with Kafka.

<div style="text-align:center"><img src="micronaut-kafka.png" /></div>
<p style="text-align: center;"><I>Figure 1: Micronaut application with Kafka</I></p>

## Running The Demo

The project requires Java 21 to build.

Start the Kafka and Zookeeper Docker containers:
```
docker-compose up -d
```

Build and test the Micronaut application, and then run, with:
```
./gradlew clean test
./gradlew run
```

Check application health:
```
curl localhost:9001/health
```

## Component Tests

The component tests bring up the application, Kafka and Zookeeper in docker containers.

For more on the component tests see: https://github.com/lydtechconsulting/component-test-framework

First ensure the Project module paths are set to `Inherit project compile output path` to ensure the fat jar is built in `./build/libs`.  Then build the Micronaut application jar, followed by the Docker container:
```
./gradlew clean build
docker build -t ct/micronaut-kafka-java:latest .
```

Run component tests:
```
./gradlew componentTest --rerun-tasks
```

Run tests leaving containers up:
```
./gradlew componentTest --rerun-tasks -Dcontainers.stayup=true
```

Note that `--rerun-tasks` is required for subsequent runs when no change has happened between test runs.

## Docker Clean Up

Manual clean up (if left containers up):
```
docker rm -f $(docker ps -aq)
```
