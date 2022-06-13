FROM openjdk

WORKDIR /app

COPY target/miniautorizador-0.0.1-SNAPSHOT.jar /app/miniautorizador.jar

ENTRYPOINT ["java", "-jar", "miniautorizador.jar"]

