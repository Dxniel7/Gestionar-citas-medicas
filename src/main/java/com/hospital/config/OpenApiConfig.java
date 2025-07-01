package com.hospital.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestión de Citas Médicas")
                        .version("1.0.0")
                        .description("""
                        <h3>Aplicación de un sistema para la gestión de citas médicas y acceso a historiales clínicos.</h3>
                        <p>Este proyecto está diseñado para manejar roles diferenciados para pacientes, doctores y recepcionistas, centralizando toda la lógica de negocio en esta API.</p>
                        ---
                        <h3>Creadores:</h3>
                        <ul>
                            <li>Cardoso Osorio Atl Yosafat</li>
                            <li>García Cárdenas Ángel Alberto</li>
                            <li>Guzmán Mares Marian Kelly</li>
                            <li>Hernández Vázquez Jorge Daniel</li>
                        </ul>
                        ---
                        <h3>Enlaces de Interés:</h3>
                        <ul>
                            <li><a href="https://github.com/Dxniel7/Gestionar-citas-medicas" target="_blank"><strong>Repositorio en GitHub Backend</strong></a></li>
                            <li><a href="https://github.com/ATL1GOD/Gestion-Citas" target="_blank"><strong>Repositorio en GitHub Frontend</strong></a></li>
                            <li><a href="https://bespoke-semifreddo-24ffa5.netlify.app/" target="_blank"><strong>Aplicación Frontend (Cliente)</strong></a></li>
                            <li><a href="https://gestionar-citas-medicas.onrender.com/" target="_blank"><strong>Servidor de producción en Render API</strong></a></li>
                        </ul>
                        ---
                        <h3>Reportes en PDF:</h3>
                        <p>La API incluye endpoints para generar reportes en formato PDF para diferentes entidades (doctores, pacientes, citas, etc.). Puedes encontrarlos y probarlos en la sección <code><strong>ReporteController</strong></code> más abajo.</p>
                        """)
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/license/mit/")))
                .servers(List.of(
                        new Server().url("https://gestionar-citas-medicas-production.up.railway.app").description("Servidor de Producción (Railway)"),
                        new Server().url("http://localhost:8042").description("Servidor Local de Desarrollo")
                ));
    }
}