package com.example.service;

import com.example.model.dto.BookDto;

import java.time.OffsetDateTime;
import java.util.List;

public interface BookService {

    BookDto create(BookDto bookDto);

    BookDto addBookToAuthor(String bookId, String authorId);

    BookDto updateById(String id, BookDto bookDto);

    BookDto getById(String id);

    List<BookDto> search(
        String pattern,
        OffsetDateTime start,
        OffsetDateTime end,
        List<String> ids
    );

    void deleteById(String id);

    void removeBookFromAuthor(String bookId, String authorId);

}
