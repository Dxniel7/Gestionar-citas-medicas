package com.hospital.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${DB_URL}")
    private String dbUrl;

    @Value("${DB_USERNAME}")
    private String dbUsername;

    @Value("${DB_PASSWORD}")
    private String dbPassword;

    public DataSourceConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    @Primary // Marca este bean como el DataSource principal
    public HikariDataSource dataSource() throws IOException {
        HikariDataSource dataSource = new HikariDataSource();

        // Configura la URL, usuario y contraseña desde las variables de entorno
        dataSource.setJdbcUrl(dbUrl);  // Asignar DB_URL
        dataSource.setUsername(dbUsername);  // Asignar DB_USERNAME
        dataSource.setPassword(dbPassword);  // Asignar DB_PASSWORD

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
