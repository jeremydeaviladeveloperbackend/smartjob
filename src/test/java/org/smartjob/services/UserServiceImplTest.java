package org.smartjob.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.smartjob.dao.UserDao;
import org.smartjob.dao.entities.User;
import org.smartjob.dao.mapper.UserMapper;
import org.smartjob.dto.PhoneDto;
import org.smartjob.exceptions.SmartJobException;
import org.smartjob.models.UserRequest;
import org.smartjob.models.UserResponse;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserServiceImpl Tests")
class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRequest userRequest;
    private User userEntity;
    private UserResponse userResponse;
    private User savedUser;

    @BeforeEach
    void setUp() {
        userRequest = createValidUserRequest();
        userEntity = createUserEntity();
        userResponse = createUserResponse();
        savedUser = createSavedUser();
    }

    @Test
    @DisplayName("Debería crear un usuario exitosamente")
    void shouldCreateUserSuccessfully() {
        
        when(userDao.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userMapper.userDtoToUserEntitie(any(UserRequest.class))).thenReturn(userEntity);
        when(userDao.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.userEntititeToUserDto(any(User.class))).thenReturn(userResponse);

        
        UserResponse result = userService.createUser(userRequest);

        
        assertNotNull(result);
        assertEquals(userResponse.getId(), result.getId());
        assertEquals(userResponse.getToken(), result.getToken());
        
        verify(userDao, times(1)).findByEmail(userRequest.getEmail());
        verify(userDao, times(1)).save(any(User.class));
        verify(userMapper, times(1)).userDtoToUserEntitie(userRequest);
        verify(userMapper, times(1)).userEntititeToUserDto(savedUser);
    }

    @Test
    @DisplayName("Debería establecer campos automáticos al crear usuario")
    void shouldSetAutomaticFieldsWhenCreatingUser() {
        
        User userToSave = createUserEntity();
        when(userDao.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userMapper.userDtoToUserEntitie(any(UserRequest.class))).thenReturn(userToSave);
        when(userDao.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            assertNotNull(user.getToken());
            assertTrue(user.getIsactive());
            assertNotNull(user.getCreated());
            assertNotNull(user.getModified());
            assertNotNull(user.getLastLogin());
            return savedUser;
        });
        when(userMapper.userEntititeToUserDto(any(User.class))).thenReturn(userResponse);

        
        userService.createUser(userRequest);

        
        verify(userDao, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Debería lanzar SmartJobException cuando el email ya existe")
    void shouldThrowSmartJobExceptionWhenEmailExists() {
        
        User existingUser = createUserEntity();
        when(userDao.findByEmail(anyString())).thenReturn(Optional.of(existingUser));

       
        SmartJobException exception = assertThrows(
                SmartJobException.class,
                () -> userService.createUser(userRequest)
        );

        String message = exception.getMessage();
        assertNotNull(message, "El mensaje de la excepción no debe ser null");
        assertTrue(message.contains("ya se encuentra registrado"), 
                "El mensaje debe contener 'ya se encuentra registrado'");
        verify(userDao, times(1)).findByEmail(userRequest.getEmail());
        verify(userDao, never()).save(any(User.class));
        verify(userMapper, never()).userDtoToUserEntitie(any(UserRequest.class));
        verify(userMapper, never()).userEntititeToUserDto(any(User.class));
    }

    @Test
    @DisplayName("Debería lanzar SmartJobException cuando ocurre un error general")
    void shouldThrowSmartJobExceptionOnGeneralError() {
        
        when(userDao.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userMapper.userDtoToUserEntitie(any(UserRequest.class))).thenReturn(userEntity);
        when(userDao.save(any(User.class))).thenThrow(new RuntimeException("Database error"));

       
        assertThrows(
                SmartJobException.class,
                () -> userService.createUser(userRequest)
        );

        verify(userDao, times(1)).findByEmail(userRequest.getEmail());
        verify(userDao, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Debería convertir ExistentEntityException a SmartJobException")
    void shouldConvertExistentEntityExceptionToSmartJobException() {
        
        when(userDao.findByEmail(anyString())).thenReturn(Optional.of(createUserEntity()));

       
        SmartJobException exception = assertThrows(
                SmartJobException.class,
                () -> userService.createUser(userRequest)
        );

        String message = exception.getMessage();
        assertNotNull(message, "El mensaje de la excepción no debe ser null");
        assertTrue(message.contains("ya se encuentra registrado"), 
                "El mensaje debe contener 'ya se encuentra registrado'");
        verify(userDao, times(1)).findByEmail(userRequest.getEmail());
        verify(userMapper, never()).userDtoToUserEntitie(any(UserRequest.class));
        verify(userDao, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Debería validar email antes de crear usuario")
    void shouldValidateEmailBeforeCreatingUser() {
        
        String email = "test@example.com";
        userRequest.setEmail(email);
        when(userDao.findByEmail(email)).thenReturn(Optional.empty());
        when(userMapper.userDtoToUserEntitie(any(UserRequest.class))).thenReturn(userEntity);
        when(userDao.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.userEntititeToUserDto(any(User.class))).thenReturn(userResponse);

        
        userService.createUser(userRequest);

        
        verify(userDao, times(1)).findByEmail(email);
        verify(userDao, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Debería llamar al mapper para convertir UserRequest a User")
    void shouldCallMapperToConvertUserRequestToUser() {
        
        when(userDao.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userMapper.userDtoToUserEntitie(any(UserRequest.class))).thenReturn(userEntity);
        when(userDao.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.userEntititeToUserDto(any(User.class))).thenReturn(userResponse);

        
        userService.createUser(userRequest);

        
        verify(userMapper, times(1)).userDtoToUserEntitie(userRequest);
    }

    @Test
    @DisplayName("Debería llamar al mapper para convertir User a UserResponse")
    void shouldCallMapperToConvertUserToUserResponse() {
        
        when(userDao.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userMapper.userDtoToUserEntitie(any(UserRequest.class))).thenReturn(userEntity);
        when(userDao.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.userEntititeToUserDto(any(User.class))).thenReturn(userResponse);

        
        userService.createUser(userRequest);

        
        verify(userMapper, times(1)).userEntititeToUserDto(savedUser);
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
        request.setPhone(phones);
        
        return request;
    }

    private User createUserEntity() {
        User user = new User();
        user.setName("Juan Pérez");
        user.setEmail("juan.perez@example.com");
        user.setPassword("Password123!");
        return user;
    }

    private User createSavedUser() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Juan Pérez");
        user.setEmail("juan.perez@example.com");
        user.setPassword("Password123!");
        user.setToken(UUID.randomUUID());
        user.setIsactive(true);
        user.setCreated(Instant.now());
        user.setModified(Instant.now());
        user.setLastLogin(Instant.now());
        return user;
    }

    private UserResponse createUserResponse() {
        UserResponse response = new UserResponse();
        response.setId(UUID.randomUUID().toString());
        response.setToken(UUID.randomUUID().toString());
        response.setCreated("2024-01-01T00:00:00Z");
        response.setModified("2024-01-01T00:00:00Z");
        response.setLastLogin("2024-01-01T00:00:00Z");
        response.setIsActive("true");
        return response;
    }
}

