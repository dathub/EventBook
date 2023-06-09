package com.dtcode.eventbook.domain.security;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String permission;

    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles;

}

//References:
//https://www.baeldung.com/jpa-cascade-types

//https://www.baeldung.com/hibernate-identifiers
//Identifiers in Hibernate represent the primary key of an entity.
//The most straightforward way to define an identifier is by using the @Id annotation.
//If we want to automatically generate the primary key value, we can add the @GeneratedValue annotation.
//This can use four generation types: AUTO, IDENTITY, SEQUENCE and TABLE.
//If we don't explicitly specify a value, the generation type defaults to AUTO.
//
//AUTO Generation -  the persistence provider will determine values based on the type of the primary key attribute.
//        This type can be numerical or UUID.
//IDENTITY Generation - relies on the IdentityGenerator, which expects values generated by an identity column in the database.
//        This means they are auto-incremented. One thing to note is that IDENTITY generation disables batch updates.
//SEQUENCE Generation - This generator uses sequences if our database supports them.
//        It switches to table generation if they aren't supported.
//        SEQUENCE is the generation type recommended by the Hibernate documentation.
//TABLE Generation - uses an underlying database table that holds segments of identifier generation values.