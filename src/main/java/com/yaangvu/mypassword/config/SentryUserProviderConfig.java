package com.yaangvu.mypassword.config;

import com.yaangvu.mypassword.services.imp.UserServiceImp;
import io.sentry.protocol.User;
import io.sentry.spring.SentryUserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class SentryUserProviderConfig implements SentryUserProvider {
    @Autowired
    UserServiceImp userServiceImp;
    
    @Override
    public User provideUser() {
        User user = new User();
        com.yaangvu.mypassword.models.User currentUser = userServiceImp.getOrAddCurrentUser();
        // ... set user information
        user.setUsername(currentUser == null ? "anonymous" : currentUser.getUsername());
        
        return user;
    }
}