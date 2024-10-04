package com.example.model;

import lombok.Getter;

@Getter
public enum Gender {
    MALE, FEMALE;

    public static Gender of(String value) {
        return Gender.valueOf(value);
    }
}
