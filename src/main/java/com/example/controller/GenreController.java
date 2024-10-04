package com.example.controller;

import com.example.model.dto.GenreDto;
import com.example.service.GenreService;
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
@RequestMapping("/api/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PostMapping
    public ResponseEntity<GenreDto> create(@RequestBody GenreDto request) {
        GenreDto created = genreService.create(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{genreId}/books/{bookId}/add")
    public ResponseEntity<GenreDto> addBookToGenre(@PathVariable String genreId, @PathVariable String bookId) {
        GenreDto updated = genreService.addBookToGenre(bookId, genreId);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GenreDto>> search(
        @RequestParam(required = false) String pattern,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime start,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime end,
        @RequestParam(required = false) List<String> ids
    ) {
        List<GenreDto> genres = genreService.search(pattern, start, end, ids);
        return ResponseEntity.ok(genres);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreDto> update(@PathVariable String id, @RequestBody GenreDto request) {
        GenreDto updated = genreService.updateById(id, request);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        genreService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{genreId}/books/{bookId}/delete")
    public ResponseEntity<Void> deleteBookFromGenre(@PathVariable String genreId, @PathVariable String bookId) {
        genreService.removeBookFromGenre(bookId, genreId);
        return ResponseEntity.noContent().build();
    }
}
