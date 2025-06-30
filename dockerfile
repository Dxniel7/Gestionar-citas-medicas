# --- ETAPA DE COMPILACIÓN (BUILD STAGE)
# Usamos una imagen base de Maven con JDK 21 para compilar el código
FROM maven:3.9-eclipse-temurin-21-alpine as build
WORKDIR /app

# Copia los archivos de configuración y el código fuente.
COPY pom.xml .
COPY src ./src

# Compila la aplicación y ela mpaqueta en un .jar, omitiendo las pruebas para un despliegue más rápido.
RUN mvn clean install -DskipTests

# --- ETAPA DE EJECUCIÓN (RUN STAGE)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia el archivo JAR compilado desde la etapa de construcción a esta nueva imagen.
COPY --from=build /app/target/gestionar-citas-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto 8042
EXPOSE 8042

# Define el comando para iniciar la aplicación cuando el contenedor se ejecute.
ENTRYPOINT ["java", "-jar", "app.jar"]