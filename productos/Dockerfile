# Dockerfile para el servicio de productos

# Etapa de construcción
FROM eclipse-temurin:22-jdk AS buildstage

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml .
COPY src /app/src
COPY Wallet_LEGX51JUNINIVTKL /app/oracle_wallet

ENV TNS_ADMIN=/app/oracle_wallet

RUN mvn clean package

# Etapa de producción
FROM eclipse-temurin:22-jdk

COPY --from=buildstage /app/target/producto-0.0.1-SNAPSHOT.jar /app/producto.jar
COPY Wallet_LEGX51JUNINIVTKL /app/oracle_wallet

ENV TNS_ADMIN=/app/oracle_wallet
EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/app/producto.jar" ]