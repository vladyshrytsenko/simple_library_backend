package com.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import java.time.OffsetDateTime;

@Getter @Setter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
public abstract class Person extends Basic {

    private String email;
    private String address;
    private String phoneNumber;
    private Gender gender;
    private OffsetDateTime birthDate;
    private String socialSecurityNumber;
}
