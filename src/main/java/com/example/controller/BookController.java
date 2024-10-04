package com.example.controller;

import com.example.model.dto.BookDto;
import com.example.model.dto.BookInstanceDto;
import com.example.service.BookInstanceService;
import com.example.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookInstanceService bookInstanceService;

    @PostMapping
    public ResponseEntity<BookDto> create(@RequestBody BookDto request) {
        BookDto created = bookService.create(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/{bookId}/instance")
    public ResponseEntity<BookInstanceDto> createInstance(@PathVariable String bookId, @RequestBody BookInstanceDto request) {
        BookInstanceDto created = bookInstanceService.create(bookId, request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> search(
        @RequestParam(required = false) String pattern,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime start,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime end,
        @RequestParam(required = false) List<String> ids
    ) {
        List<BookDto> books = bookService.search(pattern, start, end, ids);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/instance")
    public ResponseEntity<List<BookInstanceDto>> searchInstances(
        @RequestParam(required = false) String pattern,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime start,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime end,
        @RequestParam(required = false) List<String> ids
    ) {
        List<BookInstanceDto> books = bookInstanceService.search(pattern, start, end, ids);
        return ResponseEntity.ok(books);
    }

    @PutMapping("/{bookId}/instance/{instanceId}")
    public ResponseEntity<BookInstanceDto> update(
        @PathVariable String bookId,
        @PathVariable String instanceId,
        @RequestBody BookInstanceDto request) {
        BookInstanceDto updated = bookInstanceService.updateById(instanceId, bookId, request);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> update(@PathVariable String id, @RequestBody BookDto request) {
        BookDto updated = bookService.updateById(id, request);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PutMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<BookDto> addBookToAuthor(@PathVariable String bookId, @PathVariable String authorId) {
        BookDto updated = bookService.addBookToAuthor(bookId, authorId);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}/authors/{authorId}")
    public ResponseEntity<BookDto> removeBookFromAuthor(@PathVariable String bookId, @PathVariable String authorId) {
        bookService.removeBookFromAuthor(bookId, authorId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/instance/{instanceId}")
    public ResponseEntity<Void> deleteInstance(@PathVariable String instanceId) {
        bookInstanceService.deleteById(instanceId);
        return ResponseEntity.noContent().build();
    }
}
