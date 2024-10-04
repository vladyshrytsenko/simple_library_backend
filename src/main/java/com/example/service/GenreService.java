package com.example.service;

import com.example.model.dto.GenreDto;

import java.time.OffsetDateTime;
import java.util.List;

public interface GenreService {

    GenreDto create(GenreDto genreDto);

    GenreDto addBookToGenre(String bookId, String genreId);

    GenreDto updateById(String id, GenreDto genreDto);

    List<GenreDto> search(
        String pattern,
        OffsetDateTime start,
        OffsetDateTime end,
        List<String> ids
    );

    void deleteById(String id);

    void removeBookFromGenre(String bookId, String genreId);
}
