package com.example.model.dto;

import com.example.model.Gender;
import com.example.model.entity.Author;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@Builder
public class AuthorDto {

    private String name;
    private String description;
    private OffsetDateTime dateCreated;
    private OffsetDateTime dateUpdated;
    private boolean softDelete;
    private String email;
    private String address;
    private String phoneNumber;
    private String gender;
    private OffsetDateTime birthDate;
    private String socialSecurityNumber;

    public static AuthorDto toDto(Author author) {
        if (author == null) {
            return null;
        }

        return AuthorDto.builder()
            .name(author.getName())
            .description(author.getDescription())
            .dateCreated(author.getDateCreated())
            .dateUpdated(author.getDateUpdated())
            .softDelete(author.isSoftDelete())
            .email(author.getEmail())
            .address(author.getAddress())
            .phoneNumber(author.getPhoneNumber())
            .gender(author.getGender().name())
            .birthDate(author.getBirthDate())
            .socialSecurityNumber(author.getSocialSecurityNumber())
            .build();
    }

    public static List<AuthorDto> toDtoList(List<Author> authors) {
        if (authors == null || authors.isEmpty()) {
            return List.of();
        }

        return authors.stream()
            .map(AuthorDto::toDto)
            .collect(Collectors.toList());
    }

    public static Author toEntity(AuthorDto authorDto) {
        if (authorDto == null) {
            return null;
        }

        return Author.builder()
            .name(authorDto.getName())
            .description(authorDto.getDescription())
            .dateCreated(authorDto.getDateCreated())
            .dateUpdated(authorDto.getDateUpdated())
            .softDelete(authorDto.isSoftDelete())
            .email(authorDto.getEmail())
            .address(authorDto.getAddress())
            .phoneNumber(authorDto.getPhoneNumber())
            .gender(Gender.of(authorDto.getGender()))
            .birthDate(authorDto.getBirthDate())
            .socialSecurityNumber(authorDto.getSocialSecurityNumber())
            .build();
    }
}
