package com.example.service;

import com.example.model.dto.AuthorDto;

import java.time.OffsetDateTime;
import java.util.List;

public interface AuthorService {

    AuthorDto create(AuthorDto authorDto);

    AuthorDto updateById(String id, AuthorDto authorDto);

    List<AuthorDto> search(
        String pattern,
        OffsetDateTime start,
        OffsetDateTime end,
        List<String> ids
    );

    void deleteById(String id);
}
