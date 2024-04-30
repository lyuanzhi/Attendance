FROM openjdk:17-jdk-slim as builder
RUN apt update && apt install -y findutils
RUN USER=root mkdir attendanceBack
WORKDIR /attendanceBack
COPY . .
RUN ./gradlew bootjar
# Final stage
FROM openjdk:17-jdk-slim
COPY --from=builder /attendanceBack/attendanceServer/build/libs/attendanceServer.jar .
CMD ["java", "-jar", "attendanceServer.jar"]
