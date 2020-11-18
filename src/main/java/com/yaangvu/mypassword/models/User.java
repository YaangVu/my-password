package com.yaangvu.mypassword.models;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(unique = true, nullable = false)
    private String userId;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @NotNull
    @Email
    @Column(unique = true)
    private String email;
    
    @Column(unique = true)
    private String phoneNumber;
    
    private String fullName;
    
    @NotNull
    private String password;
    
    private String encryptionKey;
    
    @Column(updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;
    
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updatedAt;
    
    @Column(columnDefinition = "boolean default false")
    @Where(clause = "is_deleted=false")
    private Boolean isDeleted;
}
