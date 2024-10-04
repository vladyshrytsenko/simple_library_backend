package com.example.model.dto;

import com.example.model.entity.LenderToBookInstance;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@Builder
public class LenderToBookInstanceDto {

    private String name;
    private String description;
    private OffsetDateTime dateCreated;
    private OffsetDateTime dateUpdated;
    private boolean softDelete;
    private String lenderId;
    private String bookInstanceId;
    private OffsetDateTime lent;
    private OffsetDateTime dueBack;
    private OffsetDateTime returned;

    public static LenderToBookInstanceDto toDto(LenderToBookInstance lenderToBookInstance) {
        if (lenderToBookInstance == null) {
            return null;
        }

        return LenderToBookInstanceDto.builder()
            .name(lenderToBookInstance.getName())
            .description(lenderToBookInstance.getDescription())
            .dateCreated(lenderToBookInstance.getDateCreated())
            .dateUpdated(lenderToBookInstance.getDateUpdated())
            .softDelete(lenderToBookInstance.isSoftDelete())
            .lenderId(lenderToBookInstance.getLender().getId())
            .bookInstanceId(lenderToBookInstance.getBookInstance().getId())
            .lent(lenderToBookInstance.getLent())
            .dueBack(lenderToBookInstance.getDueBack())
            .returned(lenderToBookInstance.getReturned())
            .build();
    }

    public static List<LenderToBookInstanceDto> toDtoList(List<LenderToBookInstance> lenderToBookInstances) {
        if (lenderToBookInstances == null || lenderToBookInstances.isEmpty()) {
            return List.of();
        }

        return lenderToBookInstances.stream()
            .map(LenderToBookInstanceDto::toDto)
            .collect(Collectors.toList());
    }

    public static LenderToBookInstance toEntity(LenderToBookInstanceDto lenderToBookInstanceDto) {
        if (lenderToBookInstanceDto == null) {
            return null;
        }

        return LenderToBookInstance.builder()
            .name(lenderToBookInstanceDto.getName())
            .description(lenderToBookInstanceDto.getDescription())
            .dateCreated(lenderToBookInstanceDto.getDateCreated())
            .dateUpdated(lenderToBookInstanceDto.getDateUpdated())
            .softDelete(lenderToBookInstanceDto.isSoftDelete())
            .lent(lenderToBookInstanceDto.getLent())
            .dueBack(lenderToBookInstanceDto.getDueBack())
            .returned(lenderToBookInstanceDto.getReturned())
            .build();
    }
}
