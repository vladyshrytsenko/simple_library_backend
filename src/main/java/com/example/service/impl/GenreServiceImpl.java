package com.example.service.impl;

import com.example.model.dto.BookToGenreDto;
import com.example.model.dto.GenreDto;
import com.example.model.entity.Book;
import com.example.model.entity.BookToGenre;
import com.example.model.entity.Genre;
import com.example.service.GenreService;
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
public class GenreServiceImpl implements GenreService {

    private final EntityManager em;

    @Override
    @Transactional
    public GenreDto create(GenreDto genreDto) {
        Genre genre = GenreDto.toEntity(genreDto);

        em.persist(genre);
        if (genreDto.getBookIds()!= null && !genreDto.getBookIds().isEmpty()) {
            genreDto.getBookIds().forEach(id -> {
                addBookToGenre(id, genre.getId());
            });
        }

        return GenreDto.toDto(genre);
    }

    @Override
    @Transactional
    public GenreDto addBookToGenre(String bookId, String genreId) {
        Book bookFound = em.find(Book.class, bookId);
        if (bookFound == null) {
            throw new RuntimeException("Book not found!");
        }

        Genre foundGenre = em.find(Genre.class, genreId);
        if (foundGenre == null) {
            throw new RuntimeException("Genre not found!");
        }

        BookToGenreDto bookToGenreDto = BookToGenreDto.builder()
            .bookId(bookId)
            .name(bookFound.getName() + "_" + foundGenre.getName())
            .dateCreated(OffsetDateTime.now())
            .dateUpdated(OffsetDateTime.now())
            .description(foundGenre.getDescription())
            .softDelete(bookFound.isSoftDelete())
            .build();

        BookToGenre createdBookToGenre = BookToGenreDto.toEntity(bookToGenreDto);
        createdBookToGenre.setId(genreId);
        createdBookToGenre.setBook(bookFound);
        createdBookToGenre.setGenre(foundGenre);
        em.persist(createdBookToGenre);

        List<BookToGenre> genreBooks = foundGenre.getBooks();
        if (genreBooks == null) {
            genreBooks = new ArrayList<>();
        }
        genreBooks.add(createdBookToGenre);
        foundGenre.setBooks(genreBooks);
        em.persist(bookFound);
        return GenreDto.toDto(foundGenre);
    }

    @Override
    @Transactional
    public GenreDto updateById(String id, GenreDto genreDto) {
        Genre found = em.find(Genre.class, id);

        if (found == null) {
            throw new RuntimeException("Genre not found!");
        }

        if (genreDto.getName() != null) {
            found.setName(genreDto.getName());
        }
        if (genreDto.getDescription() != null) {
            found.setDescription(genreDto.getDescription());
        }
        if (genreDto.getDateUpdated() != null) {
            found.setDateUpdated(genreDto.getDateUpdated());
        }

        em.merge(found);
        return GenreDto.toDto(found);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> search(
        String pattern,
        OffsetDateTime start,
        OffsetDateTime end,
        List<String> ids) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Genre> query = cb.createQuery(Genre.class);
        Root<Genre> root = query.from(Genre.class);

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

        List<Genre> resultList = em.createQuery(query).getResultList();
        return GenreDto.toDtoList(resultList);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        Genre found = em.find(Genre.class, id);

        if (found != null && !found.isSoftDelete()) {
            found.setSoftDelete(true);
            em.merge(found);
        }
    }

    @Override
    @Transactional
    public void removeBookFromGenre(String bookId, String genreId) {
        BookToGenre bookFound = em.find(BookToGenre.class, bookId);
        if (bookFound == null) {
            throw new RuntimeException("BookToGenre not found!");
        }

        Genre foundGenre = em.find(Genre.class, genreId);
        if (foundGenre == null) {
            throw new RuntimeException("Genre not found!");
        }

        bookFound.setSoftDelete(true);
        em.merge(bookFound);

        foundGenre.getBooks().remove(bookFound);

        em.persist(foundGenre);
    }
}
