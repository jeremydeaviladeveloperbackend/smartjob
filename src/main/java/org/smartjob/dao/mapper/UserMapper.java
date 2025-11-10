package org.smartjob.dao.mapper;

import org.mapstruct.Mapper;
import org.smartjob.dao.entities.User;
import org.smartjob.models.UserRequest;
import org.smartjob.models.UserResponse;

/**
 * Mapper para convertir entre DTOs y entidades de User.
 * <p>
 * Esta interfaz utiliza MapStruct para generar automáticamente la implementación
 * de mapeo entre objetos UserRequest/UserResponse y la entidad User.
 * </p>
 *
 * @author Jeremy De Avila
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convierte un UserRequest a una entidad User.
     *
     * @param dto UserRequest con los datos del usuario
     * @return Entidad User mapeada
     */
    User userDtoToUserEntitie(UserRequest dto);
    
    /**
     * Convierte una entidad User a un UserResponse.
     *
     * @param entity Entidad User
     * @return UserResponse con los datos del usuario
     */
    UserResponse userEntititeToUserDto(User entity);
}
