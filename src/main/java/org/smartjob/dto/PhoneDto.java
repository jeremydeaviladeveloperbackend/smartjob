package org.smartjob.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhoneDto {
  
    @Schema(description = "Número de teléfono", example = "123456789")
    @NotBlank(message = "number no puede estar vacío")
    private String number;
    
    @Schema(description = "Código de ciudad", example = "1")
    @NotBlank(message = "citycode no puede estar vacío")
    private String citycode;
    
    @Schema(description = "Código de país", example = "57")
    @NotBlank(message = "countrycode no puede estar vacío")
    private String countrycode;

    @Schema(description = "Identificador del usuario propietario del teléfono", example = "123e4567-e89b-12d3-a456-426614174000", hidden = true)
    private UUID userId;

}
