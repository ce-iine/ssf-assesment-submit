FROM openjdk:21-bookworm AS builder

WORKDIR /src

COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

RUN ./mvnw package -Dmvn.test.skip=true

FROM openjdk:21-bookworm 

WORKDIR /app

COPY --from=builder /src/target/eventmanagement-0.0.1-SNAPSHOT.jar app.jar

# run the application 
# define railway environment variable
ENV PORT=8080
ENV SPRING_REDIS_PORT=localhost 
ENV SPRING_REDIS_HOST=6379
ENV SPRING_REDIS_PORT=localhost SPRING_REDIS_HOST=6379
ENV SPRING_REDIS_USERNAME=NOT_SET SPRING_REDIS_PASSWORD=NOT_SET

#expose this port - reference environment variable
EXPOSE ${PORT} 

#run the program 
ENTRYPOINT SERVER_PORT=${PORT} java -jar ./app.jar