package com.example.service;

import com.example.model.dto.BookInstanceDto;

import java.time.OffsetDateTime;
import java.util.List;

public interface BookInstanceService {

    BookInstanceDto create(String bookId, BookInstanceDto bookInstanceDto);

    BookInstanceDto updateById(String id, String bookId, BookInstanceDto bookInstanceDto);

    List<BookInstanceDto> search(
        String pattern,
        OffsetDateTime start,
        OffsetDateTime end,
        List<String> ids
    );

    void deleteById(String id);
}
