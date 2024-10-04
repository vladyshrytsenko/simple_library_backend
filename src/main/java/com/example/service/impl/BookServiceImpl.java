package com.example.service.impl;

import com.example.model.dto.AuthorToBookDto;
import com.example.model.dto.BookDto;
import com.example.model.entity.Author;
import com.example.model.entity.AuthorToBook;
import com.example.model.entity.Book;
import com.example.service.BookService;
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
public class BookServiceImpl implements BookService {

    private final EntityManager em;

    @Override
    @Transactional
    public BookDto create(BookDto bookDto) {
        Book book = BookDto.toEntity(bookDto);
        em.persist(book);
        return BookDto.toDto(book);
    }

    @Override
    @Transactional
    public BookDto addBookToAuthor(String bookId, String authorId) {
        Book bookFound = em.find(Book.class, bookId);
        if (bookFound == null) {
            throw new RuntimeException("Book not found!");
        }

        Author foundAuthor = em.find(Author.class, authorId);
        if (foundAuthor == null) {
            throw new RuntimeException("Author not found!");
        }

        AuthorToBookDto authorToBookDto = AuthorToBookDto.builder()
            .bookId(bookId)
            .authorId(authorId)
            .mainAuthor(true)
            .name(foundAuthor.getName())
            .dateCreated(OffsetDateTime.now())
            .dateUpdated(OffsetDateTime.now())
            .description(foundAuthor.getDescription())
            .softDelete(foundAuthor.isSoftDelete())
            .build();

        AuthorToBook createdAuthorToBook = AuthorToBookDto.toEntity(authorToBookDto);
        createdAuthorToBook.setId(authorId);
        createdAuthorToBook.setBook(bookFound);
        createdAuthorToBook.setAuthor(foundAuthor);
        em.persist(createdAuthorToBook);

        bookFound.getAuthors().add(createdAuthorToBook);
        em.persist(bookFound);
        return BookDto.toDto(bookFound);
    }

    @Override
    @Transactional
    public BookDto updateById(String id, BookDto bookDto) {
        Book found = em.find(Book.class, id);

        if (found == null) {
            throw new RuntimeException("Book not found!");
        }

        if (bookDto.getName() != null) {
            found.setName(bookDto.getName());
        }
        if (bookDto.getDescription() != null) {
            found.setDescription(bookDto.getDescription());
        }
        if (bookDto.getDateUpdated() != null) {
            found.setDateUpdated(bookDto.getDateUpdated());
        }

        em.merge(found);
        return BookDto.toDto(found);
    }

    @Override
    public BookDto getById(String id) {
        Book found = em.find(Book.class, id);

        if (found == null) {
            throw new RuntimeException("Book not found!");
        }

        return BookDto.toDto(found);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> search(
        String pattern,
        OffsetDateTime start,
        OffsetDateTime end,
        List<String> ids) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> query = cb.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);

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

        List<Book> resultList = em.createQuery(query).getResultList();
        return BookDto.toDtoList(resultList);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        Book found = em.find(Book.class, id);

        if (found != null && !found.isSoftDelete()) {
            found.setSoftDelete(true);
            em.merge(found);
        }
    }

    @Override
    @Transactional
    public void removeBookFromAuthor(String bookId, String authorId) {
        Book bookFound = em.find(Book.class, bookId);
        if (bookFound == null) {
            throw new RuntimeException("Book not found!");
        }

        AuthorToBook foundAuthor = em.find(AuthorToBook.class, authorId);
        if (foundAuthor == null) {
            throw new RuntimeException("Author not found!");
        }

        foundAuthor.setSoftDelete(true);
        em.merge(foundAuthor);

        bookFound.getAuthors().remove(foundAuthor);
        em.persist(bookFound);
    }
}
