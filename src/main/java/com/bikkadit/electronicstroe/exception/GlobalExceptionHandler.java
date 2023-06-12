package com.bikkadit.electronicstroe.exception;


import com.bikkadit.electronicstroe.helper.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //handling to resource not found exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
    {
        log.info("globale Exceptionhandler class method for Reso.NotfoundExc start ");
        ApiResponse response = ApiResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .success(true)
                .build();
        log.info("globale Exceptionhandler class method for Reso.NotfoundExc end ");

        return new ResponseEntity(response,HttpStatus.NOT_FOUND);
    }
    //method argumentnot valid exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handelMethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        log.info("globale Exceptionhandler class handelMeth.Arg.NotValidExcp method  start ");
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String, Object> response = new HashMap<>();
        allErrors.stream().forEach(objectError -> {
            String message = objectError.getDefaultMessage();
            String field = ((FieldError) objectError).getField();
            response.put(field, message);
        });
        log.info("globale Exceptionhandler class handelMeth.Arg.NotValidExcp method  end ");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }
    //handel bad api exception
        @ExceptionHandler(BadApiRequest.class)
        public ResponseEntity<ApiResponse> handleBadApiRequest(BadApiRequest ex)
        {
            log.info("globale Exceptionhandler class handleBadApiRequest method  start ");
            ApiResponse response = ApiResponse.builder()
                    .message(ex.getMessage())
                    .status(HttpStatus.NOT_FOUND)
                    .success(false)
                    .build();
            log.info("globale Exceptionhandler class handleBadApiRequest method end ");

            return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
        }
    }













