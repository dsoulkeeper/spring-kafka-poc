./gradlew clean build
docker build --no-cache --build-arg JAR_FILE=build/libs/*.jar -t upgrad/kafka-streams-demo .