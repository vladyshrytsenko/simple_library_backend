package com.example.model.dto;

import com.example.model.entity.AuthorToBook;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@Builder
public class AuthorToBookDto {

    private String name;
    private String description;
    private OffsetDateTime dateCreated;
    private OffsetDateTime dateUpdated;
    private boolean softDelete;
    private String authorId;
    private String bookId;
    private Boolean mainAuthor;

    public static AuthorToBookDto toDto(AuthorToBook authorToBook) {
        if (authorToBook == null) {
            return null;
        }

        return AuthorToBookDto.builder()
            .name(authorToBook.getName())
            .description(authorToBook.getDescription())
            .dateCreated(authorToBook.getDateCreated())
            .dateUpdated(authorToBook.getDateUpdated())
            .softDelete(authorToBook.isSoftDelete())
            .authorId(authorToBook.getAuthor().getId())
            .bookId(authorToBook.getBook().getId())
            .mainAuthor(authorToBook.isMainAuthor())
            .build();
    }

    public static List<AuthorToBookDto> toDtoList(List<AuthorToBook> authorToBooks) {
        if (authorToBooks == null || authorToBooks.isEmpty()) {
            return List.of();
        }

        return authorToBooks.stream()
            .map(AuthorToBookDto::toDto)
            .collect(Collectors.toList());
    }

    public static AuthorToBook toEntity(AuthorToBookDto authorToBookDto) {
        if (authorToBookDto == null) {
            return null;
        }

        return AuthorToBook.builder()
            .name(authorToBookDto.getName())
            .description(authorToBookDto.getDescription())
            .dateCreated(authorToBookDto.getDateCreated())
            .dateUpdated(authorToBookDto.getDateUpdated())
            .softDelete(authorToBookDto.isSoftDelete())
            .mainAuthor(authorToBookDto.getMainAuthor())
            .build();
    }
}
