FROM openjdk:17-jdk-slim as builder
RUN apt update && apt install -y findutils
RUN USER=root mkdir attendanceBack
WORKDIR /attendanceBack
COPY . .
RUN ./gradlew bootjar
# Final stage
FROM openjdk:17-jdk-slim
RUN apt-get update && apt-get install -y fontconfig libfreetype6
COPY --from=builder /attendanceBack/attendanceServer/build/libs/attendanceServer.jar .
CMD ["java", "-jar", "attendanceServer.jar"]
