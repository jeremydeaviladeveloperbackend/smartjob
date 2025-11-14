package org.smartjob.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smartjob.controller.UserController;
import org.smartjob.models.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Slf4j
@RestControllerAdvice(assignableTypes = UserController.class)
@RequiredArgsConstructor
public class UserExceptionHandlerController {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<UserResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        UserResponse userResponse = getResponse(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
        return new ResponseEntity<>(userResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExistentEntityException.class)
    public ResponseEntity<UserResponse> exception(ExistentEntityException e) {
        UserResponse userResponse = getResponse(e.getMessage());
        return new ResponseEntity<>(userResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SmartJobException.class)
    public ResponseEntity<UserResponse> exception(@NotNull SmartJobException e, HttpServletRequest request) {
        UserResponse userResponse = getResponse(e.getMessage());
        return new ResponseEntity<>(userResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static UserResponse getResponse(String e) {
        UserResponse userResponse = new UserResponse();
        userResponse.setMensaje(e);
        return userResponse;
    }
}
