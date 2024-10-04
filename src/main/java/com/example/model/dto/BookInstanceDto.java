package com.example.model.dto;

import com.example.model.entity.BookInstance;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@Builder
public class BookInstanceDto {

    private String name;
    private String description;
    private OffsetDateTime dateCreated;
    private OffsetDateTime dateUpdated;
    private boolean softDelete;
    private String bookId;
    private String serialNumber;
    private Boolean blocked;

    public static BookInstanceDto toDto(BookInstance bookInstance) {
        if (bookInstance == null) {
            return null;
        }

        return BookInstanceDto.builder()
            .name(bookInstance.getName())
            .description(bookInstance.getDescription())
            .dateCreated(bookInstance.getDateCreated())
            .dateUpdated(bookInstance.getDateUpdated())
            .softDelete(bookInstance.isSoftDelete())
            .bookId(bookInstance.getBook().getId())
            .serialNumber(bookInstance.getSerialNumber())
            .blocked(bookInstance.isBlocked())
            .build();
    }

    public static List<BookInstanceDto> toDtoList(List<BookInstance> bookInstances) {
        if (bookInstances == null || bookInstances.isEmpty()) {
            return List.of();
        }

        return bookInstances.stream()
            .map(BookInstanceDto::toDto)
            .collect(Collectors.toList());
    }

    public static BookInstance toEntity(BookInstanceDto bookInstanceDto) {
        if (bookInstanceDto == null) {
            return null;
        }

        return BookInstance.builder()
            .name(bookInstanceDto.getName())
            .description(bookInstanceDto.getDescription())
            .dateCreated(bookInstanceDto.getDateCreated())
            .dateUpdated(bookInstanceDto.getDateUpdated())
            .softDelete(bookInstanceDto.isSoftDelete())
            .serialNumber(bookInstanceDto.getSerialNumber())
            .blocked(bookInstanceDto.getBlocked())
            .build();
    }
}
