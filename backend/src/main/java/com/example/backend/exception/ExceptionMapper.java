package com.example.backend.exception;

import com.example.backend.result.DataResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class ExceptionMapper {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DataResult<ExceptionResponse>> toResponse(Exception e) {
        e.printStackTrace();
        ExceptionResponse response;
        String generalCode = "MAIN_SERVICE";
        response = new ExceptionResponse(generalCode, e.getMessage());
        return new DataResult<>(false,"Exception occurred",response).intoResponseEntity();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DataResult<Map<String,String>>> validationHandler(MethodArgumentNotValidException e){
        e.printStackTrace();
        HashMap<String,String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(),fieldError.getDefaultMessage()));
        return new DataResult<>(false, "Validation error", errors).intoResponseEntity();
    }


}