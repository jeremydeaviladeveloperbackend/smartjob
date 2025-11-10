package org.smartjob.services;

import org.smartjob.models.UserRequest;
import org.smartjob.models.UserResponse;

/**
 * Interfaz de servicio para la gestión de usuarios.
 * <p>
 * Define el contrato para las operaciones relacionadas con usuarios,
 * incluyendo la creación de nuevos usuarios en el sistema.
 * </p>
 *
 * @author Jeremy De Avila
 * @version 1.0
 */
public interface UserService {
    
    /**
     * Crea un nuevo usuario en el sistema.
     * <p>
     * Este método valida que el email no exista previamente, crea la entidad User,
     * establece los valores por defecto (token, fechas, estado activo) y persiste
     * el usuario en la base de datos.
     * </p>
     *
     * @param userRequest Objeto UserRequest con la información del usuario a crear
     * @return UserResponse con la información del usuario creado
     * @throws org.smartjob.exceptions.ExistentEntityException si el email ya existe en el sistema
     * @throws org.smartjob.exceptions.SmartJobException si ocurre un error general durante el proceso
     */
    UserResponse createUser(UserRequest userRequest);
}
