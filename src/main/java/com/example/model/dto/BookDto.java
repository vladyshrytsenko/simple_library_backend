package com.example.model.dto;

import com.example.model.entity.AuthorToBook;
import com.example.model.entity.Book;
import com.example.model.entity.BookToGenre;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@Builder
public class BookDto {

    private String name;
    private String description;
    private OffsetDateTime dateCreated;
    private OffsetDateTime dateUpdated;
    private boolean softDelete;
    private List<String> genreIds;
    private List<String> authorIds;

    public static BookDto toDto(Book book) {
        if (book == null) {
            return null;
        }
        List<String> genreIds = new ArrayList<>();
        if (book.getGenres() != null) {
            genreIds = book.getGenres().stream()
                .map(BookToGenre::getId)
                .collect(Collectors.toList());
        }
        List<String> authorIds = new ArrayList<>();
        if (book.getAuthors() != null) {
            authorIds = book.getAuthors().stream()
                .map(AuthorToBook::getId)
                .collect(Collectors.toList());
        }

        return BookDto.builder()
            .name(book.getName())
            .description(book.getDescription())
            .dateCreated(book.getDateCreated())
            .dateUpdated(book.getDateUpdated())
            .softDelete(book.isSoftDelete())
            .genreIds(genreIds)
            .authorIds(authorIds)
            .build();
    }

    public static List<BookDto> toDtoList(List<Book> books) {
        if (books == null || books.isEmpty()) {
            return List.of();
        }

        return books.stream()
            .map(BookDto::toDto)
            .collect(Collectors.toList());
    }

    public static Book toEntity(BookDto bookDto) {
        if (bookDto == null) {
            return null;
        }

        return Book.builder()
            .name(bookDto.getName())
            .description(bookDto.getDescription())
            .dateCreated(bookDto.getDateCreated())
            .dateUpdated(bookDto.getDateUpdated())
            .softDelete(bookDto.isSoftDelete())
            .build();
    }
}
