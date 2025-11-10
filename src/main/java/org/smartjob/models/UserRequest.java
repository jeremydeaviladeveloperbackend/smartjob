package org.smartjob.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.smartjob.dto.PhoneDto;

import java.util.List;

/**
 * Modelo de solicitud para crear un nuevo usuario.
 * <p>
 * Esta clase representa los datos de entrada necesarios para crear un usuario
 * en el sistema. Incluye validaciones para asegurar la integridad de los datos.
 * </p>
 *
 * @author Jeremy De Avila
 * @version 1.0
 */
@Getter
@Setter
@Schema(description = "Solicitud para crear un nuevo usuario")
public class UserRequest {
    
    @Schema(description = "Nombre del usuario", example = "Juan Pérez")
    private String name;
    
    @Schema(description = "Email del usuario (debe ser único)", example = "juan.perez@example.com", required = true)
    @NotBlank(message = "email no puede estar vacío")
    @NotEmpty(message = "email no puede estar vacío")
    @Email(message = "El formato del email es inválido")
    @Valid
    private String email;
    
    @Schema(description = "Lista de teléfonos asociados al usuario")
    @Valid
    private List<PhoneDto> phone;
    
    /**
     * Contraseña del usuario.
     * <p>
     * Expresión regular para validar la contraseña.
     * Requiere obligatoriamente:
     * 1. Una letra minúscula.
     * 2. Una letra mayúscula.
     * 3. Un número (dígito).
     * 4. Un símbolo (carácter especial).
     * 5. Longitud entre 8 y 20 caracteres (ajustable).
     * </p>
     * <p>
     * Desglose de los Lookaheads:
     * - (?=.*[a-z]): verifica la presencia de AL MENOS una minúscula.
     * - (?=.*[A-Z]): verifica la presencia de AL MENOS una mayúscula.
     * - (?=.*[0-9]): verifica la presencia de AL MENOS un número (0-9).
     * - (?=.*[!@#$%^&*()_+\\-=\\[\\]{};':"\\|,.<>\\/?]): verifica la presencia de AL MENOS un símbolo.
     * - .{8,20}: El resto de la expresión valida la LONGITUD TOTAL de la cadena (8 a 20 caracteres).
     * - ^ y $: Aseguran que toda la cadena cumpla con los requisitos.
     * </p>
     */
    @Schema(
            description = "Contraseña del usuario. Debe tener entre 8-20 caracteres, e incluir al menos una mayúscula, una minúscula, un número y un símbolo.",
            example = "Password123!",
            required = true
    )
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\|,.<>\\/?]).{8,20}$",
            message = "La contraseña debe tener entre 8-20 caracteres, e incluir al menos una mayúscula, una minúscula, un número y un símbolo."
    )
    private String password;
}
