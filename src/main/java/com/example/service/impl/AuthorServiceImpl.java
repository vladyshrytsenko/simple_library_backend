package com.example.service.impl;

import com.example.model.Gender;
import com.example.model.dto.AuthorDto;
import com.example.model.entity.Author;
import com.example.service.AuthorService;
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
public class AuthorServiceImpl implements AuthorService {

    private final EntityManager em;

    @Override
    @Transactional
    public AuthorDto create(AuthorDto authorDto) {
        Author author = AuthorDto.toEntity(authorDto);
        em.persist(author);
        return AuthorDto.toDto(author);
    }

    @Override
    @Transactional
    public AuthorDto updateById(String id, AuthorDto authorDto) {
        Author found = em.find(Author.class, id);

        if (found == null) {
            throw new RuntimeException("Author not found!");
        }

        if (authorDto.getEmail() != null) {
            found.setEmail(authorDto.getEmail());
        }
        if (authorDto.getAddress() != null) {
            found.setAddress(authorDto.getAddress());
        }
        if (authorDto.getPhoneNumber() != null) {
            found.setPhoneNumber(authorDto.getPhoneNumber());
        }
        if (authorDto.getGender() != null) {
            found.setGender(Gender.of(authorDto.getGender()));
        }
        if (authorDto.getBirthDate() != null) {
            found.setBirthDate(authorDto.getBirthDate());
        }
        if (authorDto.getSocialSecurityNumber() != null) {
            found.setSocialSecurityNumber(authorDto.getSocialSecurityNumber());
        }

        em.merge(found);
        return AuthorDto.toDto(found);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> search(
        String pattern,
        OffsetDateTime start,
        OffsetDateTime end,
        List<String> ids) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Author> query = cb.createQuery(Author.class);
        Root<Author> root = query.from(Author.class);

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

        List<Author> resultList = em.createQuery(query).getResultList();
        return AuthorDto.toDtoList(resultList);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        Author found = em.find(Author.class, id);

        if (found != null && !found.isSoftDelete()) {
            found.setSoftDelete(true);
            em.merge(found);
        }
    }
}
