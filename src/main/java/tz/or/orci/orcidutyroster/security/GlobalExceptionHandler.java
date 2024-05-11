package tz.or.orci.orcidutyroster.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tz.or.orci.orcidutyroster.payload.response.ErrorResponseDto;
import tz.or.orci.orcidutyroster.service.Utils;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final HttpServletRequest request;
    private final Utils utils;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponseDto response = utils.generateErrorResponse(HttpStatus.BAD_REQUEST, request.getRequestURI(), "Invalid Method Arguments", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        Class<?> requiredType = exception.getRequiredType();
        Object value = exception.getValue();

        Map<String, String> errors = Map.of(
                "Required Type", requiredType == null ? "null" : requiredType.getSimpleName(),
                "Provided Value", value == null ? "null" : value.toString()
        );

        String message = "Invalid argument type for " + exception.getName();
        String path = request.getRequestURI();

        ErrorResponseDto response = utils.generateErrorResponse(HttpStatus.BAD_REQUEST, path, message, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleOtherExceptions(Exception exception) {
        ErrorResponseDto response = utils.generateErrorResponse(HttpStatus.BAD_REQUEST, request.getRequestURI(), exception.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}

