package com.yaangvu.mypassword.services;

import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Validated
public interface Service<T> {
    /**
     * Add new Entity
     *
     * @param t Entity
     *
     * @return T
     */
    T add(@Valid T t);
    
    /**
     * Update an Entity
     *
     * @param id id
     * @param t  Entity
     *
     * @return T
     */
    T update(int id, @Valid T t);
    
    /**
     * Delete an Entity
     *
     * @return void
     */
    boolean delete();
    
    /**
     * Find an Entity by id
     *
     * @param id id
     *
     * @return T
     *
     * @throws NotFoundException Notfound
     */
    Optional<T> find(Integer id) throws NotFoundException;
    
    /**
     * Find all Entity
     *
     * @param pageable pageable
     *
     * @return Page
     */
    Page<T> findAll(Pageable pageable);
}
