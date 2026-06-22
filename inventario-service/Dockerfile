FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/inventario-service-0.0.1-SNAPSHOT.jar app.jar

COPY wallet /app/wallet

ENV WALLET_PATH=/app/wallet

EXPOSE 8084

ENTRYPOINT ["java","-jar","app.jar"]