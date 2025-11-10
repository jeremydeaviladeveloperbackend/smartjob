package org.smartjob.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de OpenAPI (Swagger) para la documentación de la API.
 * <p>
 * Esta clase configura la información general de la API que se mostrará
 * en la interfaz de Swagger UI.
 * </p>
 *
 * @author Jeremy De Avila
 * @version 1.0
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configura la información de la API para Swagger.
     *
     * @return OpenAPI con la configuración de la documentación
     */
    @Bean
    public OpenAPI smartJobOpenAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Servidor de desarrollo");
        
        return new OpenAPI()
                .servers(List.of(server))
                .info(new Info()
                        .title("SmartJob API")
                        .description("API REST para la gestión de usuarios del sistema SmartJob. " +
                                "Esta API permite crear y gestionar usuarios con sus datos personales y teléfonos asociados.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Jeremy De Avila")
                                .email("jeremyedp@hotmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}

