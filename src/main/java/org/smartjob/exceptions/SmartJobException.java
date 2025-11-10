package org.smartjob.exceptions;

/**
 * Excepción personalizada base para errores en la aplicación SmartJob.
 * <p>
 * Esta excepción se utiliza para manejar errores generales en la aplicación.
 * </p>
 *
 * @author Jeremy De Avila
 * @version 1.0
 */
public class SmartJobException extends RuntimeException {

    /**
     * Constructor que crea una nueva excepción con el mensaje especificado.
     *
     * @param message Mensaje que describe el error
     */
    public SmartJobException(String message) {
        super(message);
    }
}
