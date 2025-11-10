package org.smartjob.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smartjob.exceptions.SmartJobException;
import org.smartjob.models.UserRequest;
import org.smartjob.models.UserResponse;
import org.smartjob.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para la gestión de usuarios.
 * <p>
 * Este controlador proporciona endpoints para operaciones relacionadas con usuarios,
 * incluyendo la creación de nuevos usuarios en el sistema.
 * </p>
 *
 * @author Jeremy De Avila
 * @version 1.0
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Users", description = "API para gestión de usuarios")
public class UserController {

    private final UserService userService;

    /**
     * Crea un nuevo usuario en el sistema.
     * <p>
     * Este endpoint permite registrar un nuevo usuario con su información básica,
     * incluyendo nombre, email, contraseña y teléfonos asociados.
     * </p>
     *
     * @param request Objeto UserRequest que contiene la información del usuario a crear.
     *                Debe incluir email válido, contraseña que cumpla los requisitos de seguridad,
     *                y opcionalmente una lista de teléfonos.
     * @return ResponseEntity con UserResponse que contiene la información del usuario creado,
     *         incluyendo ID, token, fechas de creación y modificación.
     * @throws SmartJobException si ocurre un error al crear el usuario
     * @apiNote El email debe ser único en el sistema. La contraseña debe cumplir con:
     *          - Mínimo 8 caracteres, máximo 20
     *          - Al menos una letra mayúscula
     *          - Al menos una letra minúscula
     *          - Al menos un número
     *          - Al menos un símbolo especial
     */
    @Operation(
            summary = "Crear un nuevo usuario",
            description = "Registra un nuevo usuario en el sistema con la información proporcionada. " +
                    "El email debe ser único y la contraseña debe cumplir con los requisitos de seguridad."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario creado exitosamente",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos (email inválido, contraseña no cumple requisitos, etc.)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor (email duplicado, error en base de datos, etc.)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            )
    })
    @PostMapping(
            value = "/user/api/v1",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserRequest request) throws SmartJobException {
        log.info("Recibida solicitud para crear usuario con email: {}", request.getEmail());
        UserResponse response = this.userService.createUser(request);
        log.info("Usuario creado exitosamente con ID: {}", response.getId());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
