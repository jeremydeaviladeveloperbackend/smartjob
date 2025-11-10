package org.smartjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación SmartJob.
 * <p>
 * Esta es la clase de inicio de la aplicación Spring Boot que contiene
 * el método main para ejecutar la aplicación.
 * </p>
 *
 * @author Jeremy De Avila
 * @version 1.0
 */
@SpringBootApplication
public class SmartjobApplication {

    /**
     * Método principal que inicia la aplicación Spring Boot.
     *
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(SmartjobApplication.class, args);
    }

}
