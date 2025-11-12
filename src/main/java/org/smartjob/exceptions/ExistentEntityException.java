package org.smartjob.exceptions;

/**
 * Excepción lanzada cuando se intenta crear una entidad que ya existe en el sistema.
 * <p>
 * Esta excepción se utiliza principalmente para indicar que un email ya está registrado
 * cuando se intenta crear un nuevo usuario.
 * </p>
 *
 * @author Jeremy De Avila
 * @version 1.0
 */
public class ExistentEntityException extends Exception {
    
    /**
     * Constructor que crea una nueva excepción con el mensaje especificado.
     *
     * @param message Mensaje que describe el error
     */
    public ExistentEntityException(String message) {
        super(message);
    }
}
