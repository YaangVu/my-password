package com.yaangvu.mypassword.services.imp;

import com.yaangvu.mypassword.models.User;
import com.yaangvu.mypassword.repositories.UserRepository;
import com.yaangvu.mypassword.services.UserService;
import com.yaangvu.mypassword.utils.RandomUtil;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@Slf4j
public class UserServiceImp implements UserService {
    @Autowired
    private AccessToken accessToken;
    
    @Autowired
    private UserRepository repository;
    
    @Override
    public User add(@Valid User user) {
        user.setEncryptionKey(RandomUtil.alphanumeric(20));
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        repository.save(user);
        log.info("Add new User: {}", user);
        
        return user;
    }
    
    @Override
    public User update(int id, @Valid User user) {
        return null;
    }
    
    @Override
    public boolean delete() {
        return false;
    }
    
    @Override
    public Optional<User> find(Integer id) throws NotFoundException {
        return repository.findById(id);
    }
    
    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }
    
    /**
     * Get Or Add Current User who are logging in
     *
     * @return User
     */
    public User getOrAddCurrentUser() {
        String userId = accessToken.getSubject();
        log.info("getOrAddCurrentUser Current User by userId: {}", userId);
        AccessToken.Access access = accessToken.getRealmAccess();
        Set<String> roles = access.getRoles();
        log.info("Role of Current User: {}", roles);
        
        User user = repository.findUserByUserId(userId);
        if (user == null) {
            user = new User();
            user.setUserId(userId);
            user.setUsername(accessToken.getPreferredUsername());
            user.setEmail(accessToken.getEmail());
            user.setFullName(accessToken.getGivenName().trim() + " " + accessToken.getFamilyName().trim());
            user = add(user);
        }
        
        return user;
    }
    
    /**
     * Get Encryption Key of Current User
     *
     * @return String
     */
    public String getOrAddCurrentUserEncryptionKey() {
        return getOrAddCurrentUser().getEncryptionKey();
    }
    
    /**
     * Get Current User who are logging in
     *
     * @return User
     */
    public User getCurrentUser() {
        if (accessToken == null)
            return null;
        
        String userId = accessToken.getSubject();
        log.info("Get Current User by userId: {}", userId);
        
        return repository.findUserByUserId(userId);
    }
}
