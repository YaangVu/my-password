package com.yaangvu.mypassword.repositories;

import com.yaangvu.mypassword.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    User findUserByUserId(String userId);
    
    List<User> findAllByUsername(String username, Pageable pageable);
}
