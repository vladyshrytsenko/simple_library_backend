package com.example.service;

import com.example.model.dto.LenderToBookInstanceDto;

import java.time.OffsetDateTime;
import java.util.List;

public interface LenderToBookInstanceService {

    LenderToBookInstanceDto create(
        String instanceId,
        LenderToBookInstanceDto lenderToBookInstanceDto
    );

    LenderToBookInstanceDto updateById(
        String id,
        String instanceId,
        LenderToBookInstanceDto lenderToBookInstanceDto
    );

    List<LenderToBookInstanceDto> search(
        String pattern,
        OffsetDateTime start,
        OffsetDateTime end,
        List<String> ids
    );

    void deleteById(String id);
}
