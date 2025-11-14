package org.smartjob.dao.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.smartjob.dao.entities.Phone;
import org.smartjob.dao.entities.User;
import org.smartjob.dto.PhoneDto;
import org.smartjob.models.UserRequest;
import org.smartjob.models.UserResponse;

/**
 * Mapper para convertir entre DTOs y entidades de Phone.
 * <p>
 * Esta interfaz utiliza MapStruct para generar automáticamente la implementación
 * de mapeo entre objetos PhoneDto y la entidad Phone.
 * </p>
 *
 * @author Jeremy De Avila
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface PhoneMapper {

    /**
     * Convierte un PhoneDto a una entidad Phone.
     *
     * @param dto PhoneDto con los datos del teléfono
     * @return Entidad Phone mapeada
     */
    Phone phoneDtoToPhoneEntitie(PhoneDto dto);

    /**
     * Convierte un Phone entity a un PhoneDto.
     *
     * @param phone Phone con los datos del teléfono
     * @return Dto Phone mapeado
     */
    @Mapping(source = "user.id", target = "userId")
    PhoneDto phoneToPhoneDto(Phone phone);

}
