package com.example.model.entity;

import com.example.model.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;

@Entity
@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@Where(clause = "soft_delete = false")
public class Lender extends Person {

    private boolean blocked;
}
