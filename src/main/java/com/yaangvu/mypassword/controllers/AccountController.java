package com.yaangvu.mypassword.controllers;

import com.yaangvu.mypassword.models.Account;
import com.yaangvu.mypassword.reqDto.AccountReqDto;
import com.yaangvu.mypassword.responses.SuccessResponse;
import com.yaangvu.mypassword.services.imp.AccountServiceImp;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    AccountServiceImp service;
    
    @GetMapping("")
    @RolesAllowed("admin")
    public String index() {
        return "index";
    }
    
    @PostMapping("")
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<SuccessResponse> add(@RequestBody AccountReqDto accountReqDto) throws URISyntaxException {
        service.addOrUpdate(accountReqDto);
        
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @RolesAllowed({"admin", "user"})
    public Optional<Account> find(@PathVariable Integer id) throws NotFoundException {
        
        return service.find(id);
    }
    
}
