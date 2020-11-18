package com.yaangvu.mypassword.repositories;

import com.yaangvu.mypassword.models.Domain;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DomainRepository extends PagingAndSortingRepository<Domain, Integer> {
    Domain findByDomain(String domain);
}
