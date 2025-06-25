// Archivo: src/main/java/com/hospital/AplicacionMedicaApplication.java

package com.hospital;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AplicacionMedicaApplication {

    public static void main(String[] args) {
        // Carga el archivo .env y lo pone en las propiedades del sistema
        // Esta es la línea corregida:
        Dotenv.configure().systemProperties().load();

        // Inicia la aplicación Spring
        SpringApplication.run(AplicacionMedicaApplication.class, args);
    }
}