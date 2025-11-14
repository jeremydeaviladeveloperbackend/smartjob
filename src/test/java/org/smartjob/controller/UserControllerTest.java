package org.smartjob.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.smartjob.dto.PhoneDto;
import org.smartjob.exceptions.SmartJobException;
import org.smartjob.models.UserRequest;
import org.smartjob.models.UserResponse;
import org.smartjob.services.UserService;
import org.smartjob.exceptions.UserExceptionHandlerController;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserController Tests")
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new UserExceptionHandlerController())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Debería crear un usuario exitosamente")
    void shouldCreateUserSuccessfully() throws Exception {
        
        UserRequest userRequest = createValidUserRequest();
        UserResponse userResponse = createValidUserResponse();

        when(userService.createUser(any(UserRequest.class))).thenReturn(userResponse);

        
        mockMvc.perform(post("/user/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.created").exists());
    }

    @Test
    @DisplayName("Debería retornar 400 cuando el email es inválido")
    void shouldReturnBadRequestWhenEmailIsInvalid() throws Exception {
        
        UserRequest userRequest = createValidUserRequest();
        userRequest.setEmail("email-invalido");

        
        mockMvc.perform(post("/user/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debería retornar 400 cuando el email está vacío")
    void shouldReturnBadRequestWhenEmailIsEmpty() throws Exception {
        
        UserRequest userRequest = createValidUserRequest();
        userRequest.setEmail("");

        
        mockMvc.perform(post("/user/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debería retornar 400 cuando la contraseña no cumple el patrón")
    void shouldReturnBadRequestWhenPasswordDoesNotMatchPattern() throws Exception {
        
        UserRequest userRequest = createValidUserRequest();
        userRequest.setPassword("password123"); // Sin mayúscula ni símbolo

        
        mockMvc.perform(post("/user/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debería manejar excepción cuando el email ya existe")
    void shouldHandleExceptionWhenEmailAlreadyExists() throws Exception {
        
        UserRequest userRequest = createValidUserRequest();
        when(userService.createUser(any(UserRequest.class)))
                .thenThrow(new SmartJobException("El correo ya se encuentra registrado"));

        
        mockMvc.perform(post("/user/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Debería manejar SmartJobException")
    void shouldHandleSmartJobException() throws Exception {
        
        UserRequest userRequest = createValidUserRequest();
        when(userService.createUser(any(UserRequest.class)))
                .thenThrow(new SmartJobException("Error general"));

        
        mockMvc.perform(post("/user/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Debería retornar 400 cuando el body está vacío")
    void shouldReturnBadRequestWhenBodyIsEmpty() throws Exception {
        
        mockMvc.perform(post("/user/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    private UserRequest createValidUserRequest() {
        UserRequest request = new UserRequest();
        request.setName("Juan Pérez");
        request.setEmail("juan.perez@example.com");
        request.setPassword("Password123!");
        
        PhoneDto phoneDto = new PhoneDto();
        phoneDto.setNumber("123456789");
        phoneDto.setCitycode("1");
        phoneDto.setCountrycode("57");
        
        List<PhoneDto> phones = new ArrayList<>();
        phones.add(phoneDto);
        request.setPhones(phones);
        
        return request;
    }

    private UserResponse createValidUserResponse() {
        UserResponse response = new UserResponse();
        response.setId(UUID.randomUUID().toString());
        response.setToken(UUID.randomUUID().toString());
        response.setCreated("2024-01-01T00:00:00Z");
        response.setModified("2024-01-01T00:00:00Z");
        response.setLastLogin("2024-01-01T00:00:00Z");
        response.setIsActive(true);
        return response;
    }
}

