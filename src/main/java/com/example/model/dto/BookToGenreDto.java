package com.example.model.dto;

import com.example.model.entity.BookToGenre;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@Builder
public class BookToGenreDto {

    private String name;
    private String description;
    private OffsetDateTime dateCreated;
    private OffsetDateTime dateUpdated;
    private boolean softDelete;
    private String bookId;
    private String genreId;

    public static BookToGenreDto toDto(BookToGenre bookToGenre) {
        if (bookToGenre == null) {
            return null;
        }

        return BookToGenreDto.builder()
            .name(bookToGenre.getName())
            .description(bookToGenre.getDescription())
            .dateCreated(bookToGenre.getDateCreated())
            .dateUpdated(bookToGenre.getDateUpdated())
            .softDelete(bookToGenre.isSoftDelete())
            .bookId(bookToGenre.getBook().getId())
            .genreId(bookToGenre.getGenre().getId())
            .build();
    }

    public static List<BookToGenreDto> toDtoList(List<BookToGenre> bookToGenres) {
        if (bookToGenres == null || bookToGenres.isEmpty()) {
            return List.of();
        }

        return bookToGenres.stream()
            .map(BookToGenreDto::toDto)
            .collect(Collectors.toList());
    }

    public static BookToGenre toEntity(BookToGenreDto bookToGenreDto) {
        if (bookToGenreDto == null) {
            return null;
        }

        return BookToGenre.builder()
            .name(bookToGenreDto.getName())
            .description(bookToGenreDto.getDescription())
            .dateCreated(bookToGenreDto.getDateCreated())
            .dateUpdated(bookToGenreDto.getDateUpdated())
            .softDelete(bookToGenreDto.isSoftDelete())
            .build();
    }

}
