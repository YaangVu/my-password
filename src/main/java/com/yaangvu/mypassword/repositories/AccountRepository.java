package com.yaangvu.mypassword.repositories;

import com.yaangvu.mypassword.models.Account;
import com.yaangvu.mypassword.models.Domain;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountRepository extends PagingAndSortingRepository<Account, Integer> {
    Account findByDomainAndUsername(Domain domain, String username);
}
