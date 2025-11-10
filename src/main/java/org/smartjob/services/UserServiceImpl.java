package org.smartjob.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smartjob.dao.UserDao;
import org.smartjob.dao.entities.User;
import org.smartjob.dao.mapper.UserMapper;
import org.smartjob.exceptions.ExistentEntityException;
import org.smartjob.exceptions.SmartJobException;
import org.smartjob.models.UserRequest;
import org.smartjob.models.UserResponse;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementación del servicio de usuarios.
 * <p>
 * Esta clase contiene la lógica de negocio para la gestión de usuarios,
 * incluyendo validaciones, transformaciones de datos y persistencia.
 * </p>
 *
 * @author Jeremy De Avila
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;

    /**
     * {@inheritDoc}
     * <p>
     * Implementación que valida la existencia del email, crea la entidad User,
     * establece valores por defecto y persiste el usuario.
     * </p>
     *
     * @param userRequest Información del usuario a crear
     * @return UserResponse con los datos del usuario creado
     * @throws ExistentEntityException si el email ya existe
     * @throws SmartJobException si ocurre un error general
     */
    @Override
    public UserResponse createUser(UserRequest userRequest) {
        try {
            log.debug("Iniciando creación de usuario para email: {}", userRequest.getEmail());
            
            // Validar que el email no exista
            this.validateIfMailExist(userRequest.getEmail());
            
            // Mapear UserRequest a entidad User
            User user = this.userMapper.userDtoToUserEntitie(userRequest);
            
            // Establecer valores por defecto
            user.setIsactive(true);
            user.setToken(UUID.randomUUID());
            user.setCreated(Instant.now());
            user.setModified(Instant.now());
            user.setLastLogin(user.getCreated());
            
            // Persistir usuario
            User savedUser = this.userDao.save(user);
            log.debug("Usuario guardado con ID: {}", savedUser.getId());
            
            // Mapear entidad User a UserResponse
            UserResponse response = this.userMapper.userEntititeToUserDto(savedUser);
            
            log.info("Usuario creado exitosamente con ID: {}", response.getId());
            return response;
            
        } catch (ExistentEntityException e) {
            log.warn("Error al crear usuario: {}", e.getMessage());
            throw new SmartJobException(e.getMessage());
        } catch (Exception e) {
            log.error("Error general al crear usuario: {}", e.getMessage(), e);
            String errorMessage = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
            throw new SmartJobException(errorMessage);
        }
    }

    /**
     * Valida si un email ya existe en el sistema.
     * <p>
     * Este método consulta la base de datos para verificar si ya existe
     * un usuario con el email proporcionado.
     * </p>
     *
     * @param email Email a validar
     * @throws ExistentEntityException si el email ya existe en el sistema
     */
    private void validateIfMailExist(String email) throws ExistentEntityException {
        Optional<User> user = this.userDao.findByEmail(email);
        if (user.isPresent()) {
            String errorMessage = "El correo " + email + " ya se encuentra registrado";
            log.warn("Intento de registro con email duplicado: {}", email);
            throw new ExistentEntityException(errorMessage);
        }
        log.debug("Email {} validado correctamente, no existe en el sistema", email);
    }
}
