FROM maven:3.6.3-jdk-8
WORKDIR /app
COPY . /app/
RUN mvn clean package -DskipTests
EXPOSE 8080
CMD ["mvn", "spring-boot:run"]