package org.smartjob.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * DTO (Data Transfer Object) para información de teléfonos.
 * <p>
 * Esta clase representa la información de un teléfono asociado a un usuario.
 * </p>
 *
 * @author Jeremy De Avila
 * @version 1.0
 */
@Getter
@Setter
@Schema(description = "Información de teléfono de un usuario")
public class PhoneDto {
  
    @Schema(description = "Número de teléfono", example = "123456789")
    private String number;
    
    @Schema(description = "Código de ciudad", example = "1")
    private String citycode;
    
    @Schema(description = "Código de país", example = "57")
    private String countrycode;

    @Schema(description = "Identificador del usuario propietario del teléfono", example = "123e4567-e89b-12d3-a456-426614174000", hidden = true)
    private UUID userId;

}
