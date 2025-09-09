package com.suniacode.java.ecom.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Entity(name = "users_table")
//Can change to @Entity as well
//@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role = UserRole.CUSTOMER;

    // create address field of type Address for relationship with Address entity
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    // create local date time fields for createdAt and updatedAt
    @CreationTimestamp
    private java.time.LocalDateTime createdAt;
    @UpdateTimestamp
    private java.time.LocalDateTime updatedAt;

}
