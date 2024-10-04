package com.example.service.impl;

import com.example.model.dto.LenderDto;
import com.example.model.dto.LenderToBookInstanceDto;
import com.example.model.entity.BookInstance;
import com.example.model.entity.Lender;
import com.example.model.entity.LenderToBookInstance;
import com.example.service.LenderService;
import com.example.service.LenderToBookInstanceService;
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
public class LenderToBookInstanceServiceImpl implements LenderToBookInstanceService {

    private final EntityManager em;
    private final LenderService lenderService;

    @Override
    @Transactional
    public LenderToBookInstanceDto create(
        String instanceId,
        LenderToBookInstanceDto lenderToBookInstanceDto) {

        BookInstance bookInstance = em.find(BookInstance.class, instanceId);

        if (bookInstance == null) {
            throw new RuntimeException("BookInstance not found!");
        }

        LenderToBookInstance lenderToInstance = LenderToBookInstanceDto.toEntity(lenderToBookInstanceDto);

        lenderToInstance.setBookInstance(bookInstance);

        LenderDto lenderById = lenderService.getById(lenderToBookInstanceDto.getLenderId());
        Lender lender = LenderDto.toEntity(lenderById);

        Lender managedLender = em.find(Lender.class, lenderToBookInstanceDto.getLenderId());
        if (managedLender == null) {
            em.persist(lender);
            lenderToInstance.setLender(lender);
        } else {
            lenderToInstance.setLender(managedLender);
        }

        em.persist(lenderToInstance);
        return LenderToBookInstanceDto.toDto(lenderToInstance);
    }


    @Override
    @Transactional
    public LenderToBookInstanceDto updateById(String id, String instanceId, LenderToBookInstanceDto lenderToBookInstanceDto) {
        BookInstance instanceFound = em.find(BookInstance.class, instanceId);
        if (instanceFound == null) {
            throw new RuntimeException("BookInstance not found!");
        }

        LenderToBookInstance found = em.find(LenderToBookInstance.class, id);
        if (found == null) {
            throw new RuntimeException("LenderToBookInstance not found!");
        }

        if (lenderToBookInstanceDto.getName() != null) {
            found.setName(lenderToBookInstanceDto.getName());
        }
        if (lenderToBookInstanceDto.getDescription() != null) {
            found.setDescription(lenderToBookInstanceDto.getDescription());
        }
        if (lenderToBookInstanceDto.getDateUpdated() != null) {
            found.setDateUpdated(lenderToBookInstanceDto.getDateUpdated());
        }
        if (lenderToBookInstanceDto.getLent() != null) {
            found.setLent(lenderToBookInstanceDto.getLent());
        }
        if (lenderToBookInstanceDto.getDueBack() != null) {
            found.setDueBack(lenderToBookInstanceDto.getDueBack());
        }
        if (lenderToBookInstanceDto.getReturned() != null) {
            found.setReturned(lenderToBookInstanceDto.getReturned());
        }

        em.merge(found);
        return LenderToBookInstanceDto.toDto(found);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LenderToBookInstanceDto> search(
        String pattern,
        OffsetDateTime start,
        OffsetDateTime end,
        List<String> ids) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LenderToBookInstance> query = cb.createQuery(LenderToBookInstance.class);
        Root<LenderToBookInstance> root = query.from(LenderToBookInstance.class);

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

        List<LenderToBookInstance> resultList = em.createQuery(query).getResultList();
        return LenderToBookInstanceDto.toDtoList(resultList);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        LenderToBookInstance found = em.find(LenderToBookInstance.class, id);

        if (found != null && !found.isSoftDelete()) {
            found.setSoftDelete(true);
            em.merge(found);
        }
    }
}
