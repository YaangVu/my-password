package com.yaangvu.mypassword.resDto;

import lombok.Data;

import java.util.Date;

@Data
public class AccountResDto {
    private String url;
    
    private String username;
    
    private String password;
    
    private Date updatedAt;
}
