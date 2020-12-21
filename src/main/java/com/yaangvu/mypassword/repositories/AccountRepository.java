package com.yaangvu.mypassword.repositories;

import com.yaangvu.mypassword.models.Account;
import com.yaangvu.mypassword.models.Domain;
import com.yaangvu.mypassword.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountRepository extends PagingAndSortingRepository<Account, Integer> {
    Account findByDomainAndUsername(Domain domain, String username);
    
    Page<Account> findAllByCreatedBy(User user, Pageable pageable);
}
