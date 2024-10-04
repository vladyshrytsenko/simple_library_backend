package com.example.controller;

import com.example.model.dto.LenderDto;
import com.example.model.dto.LenderToBookInstanceDto;
import com.example.service.LenderService;
import com.example.service.LenderToBookInstanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/lenders")
@RequiredArgsConstructor
public class LenderController {

    private final LenderService lenderService;
    private final LenderToBookInstanceService lenderToBookInstanceService;

    @PostMapping
    public ResponseEntity<LenderDto> create(@RequestBody LenderDto request) {
        LenderDto created = lenderService.create(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/{lenderId}/instance")
    public ResponseEntity<LenderToBookInstanceDto> create(@PathVariable String lenderId, @RequestBody LenderToBookInstanceDto request) {
        LenderToBookInstanceDto created = lenderToBookInstanceService.create(lenderId, request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LenderDto>> search(
        @RequestParam(required = false) String pattern,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime start,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime end,
        @RequestParam(required = false) List<String> ids
    ) {
        List<LenderDto> genres = lenderService.search(pattern, start, end, ids);
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/instance")
    public ResponseEntity<List<LenderToBookInstanceDto>> searchInstances(
        @RequestParam(required = false) String pattern,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime start,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime end,
        @RequestParam(required = false) List<String> ids
    ) {
        List<LenderToBookInstanceDto> books = lenderToBookInstanceService.search(pattern, start, end, ids);
        return ResponseEntity.ok(books);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LenderDto> update(@PathVariable String id, @RequestBody LenderDto request) {
        LenderDto updated = lenderService.updateById(id, request);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PutMapping("/{lenderId}/instance/{instanceId}")
    public ResponseEntity<LenderToBookInstanceDto> update(
        @PathVariable String lenderId,
        @PathVariable String instanceId,
        @RequestBody LenderToBookInstanceDto request) {
        LenderToBookInstanceDto updated = lenderToBookInstanceService.updateById(lenderId, instanceId, request);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        lenderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/instance/{instanceId}")
    public ResponseEntity<Void> deleteInstance(@PathVariable String instanceId) {
        lenderToBookInstanceService.deleteById(instanceId);
        return ResponseEntity.noContent().build();
    }
}
