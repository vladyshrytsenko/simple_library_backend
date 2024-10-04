package com.example.model.dto;

import com.example.model.entity.BookToGenre;
import com.example.model.entity.Genre;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@Builder
public class GenreDto {

    private String name;
    private String description;
    private OffsetDateTime dateCreated;
    private OffsetDateTime dateUpdated;
    private boolean softDelete;
    private List<String> bookIds;

    public static GenreDto toDto(Genre genre) {
        if (genre == null) {
            return null;
        }

        List<String> bookIds = new ArrayList<>();
        if (genre.getBooks() != null) {
            bookIds = genre.getBooks().stream()
                .map(BookToGenre::getId)
                .collect(Collectors.toList());
        }

        return GenreDto.builder()
            .name(genre.getName())
            .description(genre.getDescription())
            .dateCreated(genre.getDateCreated())
            .dateUpdated(genre.getDateUpdated())
            .softDelete(genre.isSoftDelete())
            .bookIds(bookIds)
            .build();
    }

    public static List<GenreDto> toDtoList(List<Genre> genres) {
        if (genres == null || genres.isEmpty()) {
            return List.of();
        }

        return genres.stream()
            .map(GenreDto::toDto)
            .collect(Collectors.toList());
    }

    public static Genre toEntity(GenreDto genreDto) {
        if (genreDto == null) {
            return null;
        }

        return Genre.builder()
            .name(genreDto.getName())
            .description(genreDto.getDescription())
            .dateCreated(genreDto.getDateCreated())
            .dateUpdated(genreDto.getDateUpdated())
            .softDelete(genreDto.isSoftDelete())
            .build();
    }

}
