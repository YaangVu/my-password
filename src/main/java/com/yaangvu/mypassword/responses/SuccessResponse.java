package com.yaangvu.mypassword.responses;

import com.yaangvu.mypassword.utils.Translation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class SuccessResponse {
    @Autowired
    Translation translation;
    
    public Integer code = HttpStatus.OK.value();
    
    public String message = Translation.trans("success");
    
}
