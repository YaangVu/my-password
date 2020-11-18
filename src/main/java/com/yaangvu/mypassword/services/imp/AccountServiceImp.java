package com.yaangvu.mypassword.services.imp;

import com.yaangvu.mypassword.models.Account;
import com.yaangvu.mypassword.models.Domain;
import com.yaangvu.mypassword.repositories.AccountRepository;
import com.yaangvu.mypassword.reqDto.AccountReqDto;
import com.yaangvu.mypassword.services.AccountService;
import com.yaangvu.mypassword.utils.EncryptionUtil;
import com.yaangvu.mypassword.utils.Translation;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class AccountServiceImp implements AccountService {
    @Autowired
    AccountRepository repository;
    
    @Autowired
    UserServiceImp userService;
    
    @Autowired
    DomainServiceImp domainService;
    
    @Override
    public Account add(@Valid Account account) {
        account.setCreatedAt(new Date());
        account.setUpdatedAt(new Date());
        account.setCreatedBy(userService.getOrAddCurrentUser());
        repository.save(account);
        log.info("{} Add new Account: {}", new Date(), account);
        
        return account;
    }
    
    @Override
    public Account update(int id, @Valid Account account) {
        return null;
    }
    
    public Account update(@Valid Account account) {
        account.setUpdatedAt(new Date());
        repository.save(account);
        log.info("{} Update Account: {}", new Date(), account);
        
        return account;
    }
    
    @Override
    public boolean delete() {
        return false;
    }
    
    @Override
    public Optional<Account> find(Integer id) throws NotFoundException {
        Optional<Account> account = repository.findById(id);
        log.info("{} Find Account by ID: {}, result: {}", new Date(), id, account);
        
        if (account.isPresent())
            return repository.findById(id);
        else
            throw new NotFoundException(Translation.trans("account.not-found") + ": " + id);
    }
    
    @Override
    public Page<Account> findAll(Pageable pageable) {
        return null;
    }
    
    /**
     * Add or Update Account
     *
     * @param accountReqDto account
     *
     * @throws URISyntaxException Exception
     */
    public void addOrUpdate(@Valid AccountReqDto accountReqDto) throws URISyntaxException {
        log.info("{} Add Or Update Account: {}", new Date(), accountReqDto);
        
        Domain domain = domainService.findOrAdd(accountReqDto.getUrl());
        Account account = repository.findByDomainAndUsername(domain, accountReqDto.getUsername());
        String encryptionKey = userService.getOrAddCurrentUserEncryptionKey();
    
        if (account == null) {
            account = new Account();
            account.setDomain(domain);
            account.setUsername(accountReqDto.getUsername());
            account.setPassword(EncryptionUtil.encrypt(accountReqDto.getPassword(), encryptionKey));
            add(account);
        } else {
            String pass = EncryptionUtil.encrypt(accountReqDto.getPassword(), encryptionKey);
            account.setPassword(pass);
            update(account);
        }
    }
}
