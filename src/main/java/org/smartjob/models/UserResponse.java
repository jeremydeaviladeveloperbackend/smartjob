package org.smartjob.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.smartjob.dto.PhoneDto;

import java.util.List;

/**
 * Modelo de respuesta para operaciones de usuario.
 * <p>
 * Esta clase representa la información del usuario que se devuelve
 * después de realizar operaciones como la creación de un nuevo usuario.
 * </p>
 *
 * @author Jeremy De Avila
 * @version 1.0
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Respuesta con la información del usuario creado")
public class UserResponse {

    @Schema(description = "Identificador único del usuario", example = "123e4567-e89b-12d3-a456-426614174000")
    private String id;
    
    @Schema(description = "Fecha de creación del usuario", example = "2024-01-01T00:00:00Z")
    private String created;
    
    @Schema(description = "Fecha de última modificación del usuario", example = "2024-01-01T00:00:00Z")
    private String modified;
    
    @Schema(description = "Fecha del último inicio de sesión", example = "2024-01-01T00:00:00Z")
    @JsonProperty("last_login")
    private String lastLogin;
    
    @Schema(description = "Token de autenticación del usuario", example = "123e4567-e89b-12d3-a456-426614174000")
    private String token;
    
    @Schema(description = "Indica si el usuario está activo", example = "true")
    @JsonProperty("isactive")
    private Boolean isActive;

    @Schema(description = "Mensaje adicional (opcional)")
    private String mensaje;

    @Schema(description = "Nombre del usuario")
    private String name;

    @Schema(description = "Email del usuario")
    private String email;

    @Schema(description = "Email del usuario")
    private List<PhoneDto> phones;

}
