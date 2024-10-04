package com.example.model.dto;

import com.example.model.Gender;
import com.example.model.entity.Lender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@Builder
public class LenderDto {

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
    private Boolean blocked;

    public static LenderDto toDto(Lender lender) {
        if (lender == null) {
            return null;
        }

        return LenderDto.builder()
            .name(lender.getName())
            .description(lender.getDescription())
            .dateCreated(lender.getDateCreated())
            .dateUpdated(lender.getDateUpdated())
            .softDelete(lender.isSoftDelete())
            .email(lender.getEmail())
            .address(lender.getAddress())
            .phoneNumber(lender.getPhoneNumber())
            .gender(lender.getGender().name())
            .birthDate(lender.getBirthDate())
            .socialSecurityNumber(lender.getSocialSecurityNumber())
            .blocked(lender.isBlocked())
            .build();
    }

    public static List<LenderDto> toDtoList(List<Lender> lenders) {
        if (lenders == null || lenders.isEmpty()) {
            return List.of();
        }

        return lenders.stream()
            .map(LenderDto::toDto)
            .collect(Collectors.toList());
    }

    public static Lender toEntity(LenderDto lenderDto) {
        if (lenderDto == null) {
            return null;
        }

        return Lender.builder()
            .name(lenderDto.getName())
            .description(lenderDto.getDescription())
            .dateCreated(lenderDto.getDateCreated())
            .dateUpdated(lenderDto.getDateUpdated())
            .softDelete(lenderDto.isSoftDelete())
            .email(lenderDto.getEmail())
            .address(lenderDto.getAddress())
            .phoneNumber(lenderDto.getPhoneNumber())
            .gender(Gender.of(lenderDto.getGender()))
            .birthDate(lenderDto.getBirthDate())
            .socialSecurityNumber(lenderDto.getSocialSecurityNumber())
            .blocked(lenderDto.getBlocked())
            .build();
    }
}
