FROM openjdk:8-jdk-alpine
#why? - https://stackoverflow.com/questions/50288034/unsatisfiedlinkerror-tmp-snappy-1-1-4-libsnappyjava-so-error-loading-shared-li/51655643#51655643
RUN apk update && apk add --no-cache gcompat
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]