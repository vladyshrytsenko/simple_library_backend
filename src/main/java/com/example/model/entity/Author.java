package com.example.model.entity;

import com.example.model.Person;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;

@Entity
@SuperBuilder
@NoArgsConstructor
@Where(clause = "soft_delete = false")
public class Author extends Person {

}
