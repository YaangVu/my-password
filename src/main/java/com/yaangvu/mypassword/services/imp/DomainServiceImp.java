package com.yaangvu.mypassword.services.imp;

import com.yaangvu.mypassword.models.Domain;
import com.yaangvu.mypassword.repositories.DomainRepository;
import com.yaangvu.mypassword.services.DomainService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class DomainServiceImp implements DomainService {
    @Autowired
    private DomainRepository repository;
    
    @Autowired
    private UserServiceImp userService;
    
    @Override
    public Domain add(@Valid Domain domain) {
        domain.setCreatedAt(new Date());
        domain.setUpdatedAt(new Date());
        domain.setCreatedBy(userService.getOrAddCurrentUser());
        repository.save(domain);
        log.info("{} Add new Domain: {}", new Date(), domain);
        
        return domain;
    }
    
    @Override
    public Domain update(int id, @Valid Domain domain) {
        return null;
    }
    
    @Override
    public boolean delete() {
        return false;
    }
    
    @Override
    public Optional<Domain> find(Integer id) throws NotFoundException {
        return repository.findById(id);
    }
    
    @Override
    public Page<Domain> findAll(Pageable pageable) {
        return null;
    }
    
    public Domain findOrAdd(String url) throws URISyntaxException {
        log.info("{} Find Or Add Domain: {}", new Date(), url);
        
        //get domain from url
        String domainName = getDomainName(url);
        Domain domain = repository.findByDomain(domainName);
        if (domain == null) {
            domain = new Domain();
            domain.setDomain(domainName);
            domain = add(domain);
        }
        
        return domain;
    }
    
    public static String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }
}
