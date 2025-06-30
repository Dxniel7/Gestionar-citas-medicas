package com.hospital.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // <-- Importante: Importar el módulo
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary; // <-- Importante: Marcar como primario

@Configuration
public class JacksonConfig {

    @Bean
    @Primary // Le decimos a Spring que este es el ObjectMapper principal que debe usar
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        
        // 1. Registramos el módulo para manejar LocalTime, LocalDate, etc.
        objectMapper.registerModule(new JavaTimeModule());
        
        // 2. Le decimos a Jackson que no escriba las fechas como timestamps (números largos)
        // sino como texto con formato estándar (ej: "2025-06-29T22:50:00")
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        // 3. Mantenemos tu configuración original para evitar errores con beans vacíos
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        
        return objectMapper;
    }
}