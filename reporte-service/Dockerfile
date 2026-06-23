FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/*.jar app.jar

ENV WALLET_PATH=/app/wallet

ENTRYPOINT ["java","-jar","app.jar"]