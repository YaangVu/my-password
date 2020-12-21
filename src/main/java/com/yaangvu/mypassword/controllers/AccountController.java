package com.yaangvu.mypassword.controllers;

import com.yaangvu.mypassword.handler.ForbiddenException;
import com.yaangvu.mypassword.models.Account;
import com.yaangvu.mypassword.reqDto.AccountReqDto;
import com.yaangvu.mypassword.resDto.AccountResDto;
import com.yaangvu.mypassword.responses.PaginationResponse;
import com.yaangvu.mypassword.responses.SuccessResponse;
import com.yaangvu.mypassword.services.imp.AccountServiceImp;
import com.yaangvu.mypassword.services.imp.DomainServiceImp;
import com.yaangvu.mypassword.services.imp.UserServiceImp;
import com.yaangvu.mypassword.utils.EncryptionUtil;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.net.URISyntaxException;
import java.util.Set;

@RestController
@RequestMapping("/accounts")
@Slf4j
public class AccountController {
    @Autowired
    AccountServiceImp service;
    
    @Autowired
    ModelMapper modelMapper;
    
    @Autowired
    UserServiceImp userService;
    
    @Autowired
    DomainServiceImp domainService;
    
    @GetMapping("")
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<PaginationResponse> findAll(Pageable pageable) {
        Page<Account> accounts = service.findAll(pageable);
        PaginationResponse body = new PaginationResponse();
        
        return new ResponseEntity<>(body.handleList(accounts, AccountResDto.class), HttpStatus.OK);
    }
    
    /**
     * Add or Update Account
     *
     * @param accountReqDto AccountReqDto
     *
     * @return SuccessResponse
     *
     * @throws URISyntaxException URISyntaxException
     */
    @PostMapping("")
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<SuccessResponse> add(@RequestBody AccountReqDto accountReqDto) throws URISyntaxException {
        service.addOrUpdate(accountReqDto);
        
        return new ResponseEntity<>(new SuccessResponse(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    @RolesAllowed({"admin", "user"})
    public ResponseEntity<AccountResDto> find(@PathVariable Integer id) throws NotFoundException, ForbiddenException {
        Account account = service.find(id).get();
        // Get Encryption Key of current User
        String encryptionKey = userService.getOrAddCurrentUserEncryptionKey();
        // Decrypt Password and set Password to Response
        account.setPassword(EncryptionUtil.decrypt(account.getPassword(), encryptionKey));
        // Mapping data to response
        AccountResDto accountResDto = modelMapper.map(account, AccountResDto.class);
        // Get url of account
        accountResDto.setUrl(domainService.find(account.getDomain().getId()).get().getDomain());
        
        return new ResponseEntity<>(accountResDto, HttpStatus.OK);
    }
    
    @Autowired
    private AccessToken accessToken;
    
    @GetMapping("/test")
    public Set<String> getRoles(){
        AccessToken.Access access = accessToken.getRealmAccess();
        log.info("access: {}", access);
        
        return null;
    }
}
