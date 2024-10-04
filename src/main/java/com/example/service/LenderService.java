package com.example.service;

import com.example.model.dto.LenderDto;

import java.time.OffsetDateTime;
import java.util.List;

public interface LenderService {

    LenderDto create(LenderDto lenderDto);

    LenderDto updateById(String id, LenderDto lenderDto);

    LenderDto getById(String id);

    List<LenderDto> search(
        String pattern,
        OffsetDateTime start,
        OffsetDateTime end,
        List<String> ids
    );

    void deleteById(String id);
}
