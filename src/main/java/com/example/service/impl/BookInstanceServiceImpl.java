package com.example.service.impl;

import com.example.model.dto.BookInstanceDto;
import com.example.model.entity.Book;
import com.example.model.entity.BookInstance;
import com.example.service.BookInstanceService;
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
public class BookInstanceServiceImpl implements BookInstanceService {

    private final EntityManager em;

    @Override
    @Transactional
    public BookInstanceDto create(String bookId, BookInstanceDto bookInstanceDto) {
        Book bookFound = em.find(Book.class, bookId);

        if (bookFound == null) {
            throw new RuntimeException("Book not bookFound!");
        }

        BookInstance bookInstance = BookInstanceDto.toEntity(bookInstanceDto);
        bookInstance.setBook(bookFound);
        em.persist(bookInstance);
        return BookInstanceDto.toDto(bookInstance);
    }

    @Override
    @Transactional
    public BookInstanceDto updateById(String id, String bookId, BookInstanceDto bookInstanceDto) {
        BookInstance instanceFound = em.find(BookInstance.class, id);

        if (instanceFound == null) {
            throw new RuntimeException("BookInstance not found!");
        }

        Book bookFound = em.find(Book.class, bookId);

        if (bookFound == null) {
            throw new RuntimeException("Book not found!");
        }

        if (bookInstanceDto.getName() != null) {
            instanceFound.setName(bookInstanceDto.getName());
        }
        if (bookInstanceDto.getDescription() != null) {
            instanceFound.setDescription(bookInstanceDto.getDescription());
        }
        if (bookInstanceDto.getDateUpdated() != null) {
            instanceFound.setDateUpdated(bookInstanceDto.getDateUpdated());
        }
        if (bookInstanceDto.getBlocked() != null) {
            instanceFound.setBlocked(bookInstanceDto.getBlocked());
        }

        em.merge(instanceFound);
        return BookInstanceDto.toDto(instanceFound);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookInstanceDto> search(
        String pattern,
        OffsetDateTime start,
        OffsetDateTime end,
        List<String> ids) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BookInstance> query = cb.createQuery(BookInstance.class);
        Root<BookInstance> root = query.from(BookInstance.class);

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

        List<BookInstance> resultList = em.createQuery(query).getResultList();
        return BookInstanceDto.toDtoList(resultList);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        BookInstance found = em.find(BookInstance.class, id);

        if (found != null && !found.isSoftDelete()) {
            found.setSoftDelete(true);
            em.merge(found);
        }
    }
}
