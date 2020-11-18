package com.yaangvu.mypassword.responses;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Data
public class ErrorResponse {
    private Integer code = HttpStatus.BAD_REQUEST.value();
    
    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    
    private String message;
    
    public ResponseEntity<Map<String, Object>> getResponse() {
        Map<String, Object> body = new HashMap<>();
        body.put("code", code);
        body.put("message", message);
        
        return new ResponseEntity<>(body, httpStatus);
    }
}

