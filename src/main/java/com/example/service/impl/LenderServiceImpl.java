package com.example.service.impl;

import com.example.model.Gender;
import com.example.model.dto.LenderDto;
import com.example.model.entity.Lender;
import com.example.service.LenderService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LenderServiceImpl implements LenderService {

    private final EntityManager em;

    @Override
    @Transactional
    public LenderDto create(LenderDto lenderDto) {
        Lender lender = LenderDto.toEntity(lenderDto);
        em.persist(lender);
        return LenderDto.toDto(lender);
    }

    @Override
    @Transactional
    public LenderDto updateById(String id, LenderDto lenderDto) {
        Lender found = em.find(Lender.class, id);

        if (found == null) {
            throw new RuntimeException("Lender not found!");
        }

        if (lenderDto.getEmail() != null) {
            found.setEmail(lenderDto.getEmail());
        }
        if (lenderDto.getAddress() != null) {
            found.setAddress(lenderDto.getAddress());
        }
        if (lenderDto.getPhoneNumber() != null) {
            found.setPhoneNumber(lenderDto.getPhoneNumber());
        }
        if (lenderDto.getGender() != null) {
            found.setGender(Gender.of(lenderDto.getGender()));
        }
        if (lenderDto.getBirthDate() != null) {
            found.setBirthDate(lenderDto.getBirthDate());
        }
        if (lenderDto.getSocialSecurityNumber() != null) {
            found.setSocialSecurityNumber(lenderDto.getSocialSecurityNumber());
        }
        if (lenderDto.getBlocked() != null) {
            found.setBlocked(lenderDto.getBlocked());
        }

        em.merge(found);
        return LenderDto.toDto(found);
    }

    @Override
    public LenderDto getById(String id) {
        Lender found = em.find(Lender.class, id);

        if (found == null) {
            throw new RuntimeException("Lender not found!");
        }

        return LenderDto.toDto(found);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LenderDto> search(
        String pattern,
        OffsetDateTime start,
        OffsetDateTime end,
        List<String> ids) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Lender> query = cb.createQuery(Lender.class);
        Root<Lender> root = query.from(Lender.class);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotBlank(pattern)) {
            predicates.add(
                cb.like(root.get("name"), "%" + pattern + "%")
            );
        }
        if (start != null && end != null) {
            predicates.add(
                cb.between(root.get("dateCreated"), start, end)
            );
        }
        if (ids != null && !ids.isEmpty()) {
            predicates.add(
                root.get("id").in(ids)
            );
        }

        query.where(
            predicates.toArray(predicates.toArray(new Predicate[0]))
        );

        List<Lender> resultList = em.createQuery(query).getResultList();
        return LenderDto.toDtoList(resultList);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        Lender found = em.find(Lender.class, id);

        if (found != null && !found.isSoftDelete()) {
            found.setSoftDelete(true);
            em.merge(found);
        }
    }
}
