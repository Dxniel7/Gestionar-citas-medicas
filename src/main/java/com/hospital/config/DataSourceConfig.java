package com.hospital.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary; 
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class DataSourceConfig {

    private final ResourceLoader resourceLoader;

    public DataSourceConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    // Para permitir que Spring Boot maneje las propiedades básicas del DataSource
    @Bean
    @Primary // Marca este bean como el DataSource principal
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.hikari") // Para propiedades específicas de HikariCP
    public HikariDataSource dataSource(DataSourceProperties properties) throws IOException {
        HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();

        // 1. Cargar el recurso root.crt del classpath
        Resource resource = resourceLoader.getResource("classpath:root.crt");

        if (!resource.exists()) {
            throw new IOException("El archivo root.crt no se encontró en el classpath: " + resource.getDescription());
        }

        // 2. Obtener la ruta absoluta del archivo
        String rootCertAbsolutePath = resource.getFile().getAbsolutePath();

        // 3. Establecer la propiedad sslrootcert en HikariDataSource con la ruta absoluta
        // Esto es crucial para que el controlador PostgreSQL lo entienda.
        dataSource.addDataSourceProperty("sslrootcert", rootCertAbsolutePath);

        // Aseguramos que sslmode=verify-ca se mantenga si no está en la URL
        dataSource.addDataSourceProperty("sslmode", "verify-ca");

        return dataSource;
    }
}