package com.yaangvu.mypassword.responses;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class ValidationErrorResponse {
    private Map<String, String> violations = new HashMap<>();
    
    public void addViolations(String fieldName, String message) {
        String pattern = "(\\w)*\\.";
        this.violations.put(fieldName.replaceAll(pattern, ""), message);
    }
    
    private Integer code;
    
    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    
    public ResponseEntity<Map<String, Object>> getResponse() {
        Map<String, Object> body = new HashMap<>();
        
        body.put("code", this.code);
        body.put("messages", this.violations);
        
        return new ResponseEntity<>(body, httpStatus);
    }
}
