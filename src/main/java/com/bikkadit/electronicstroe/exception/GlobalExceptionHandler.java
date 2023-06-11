package com.bikkadit.electronicstroe.exception;


import com.bikkadit.electronicstroe.helper.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
    {
        log.info("globale Exception handler class method start ");
        ApiResponse response = ApiResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .success(true)
                .build();
        log.info("globale Exception handler class method end ");

        return new ResponseEntity(response,HttpStatus.NOT_FOUND);
    }

}
