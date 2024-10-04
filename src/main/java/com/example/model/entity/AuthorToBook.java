package com.example.model.entity;

import com.example.model.Basic;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@Where(clause = "soft_delete = false")
public class AuthorToBook extends Basic {

    @ManyToOne
    private Author author;

    @ManyToOne
    private Book book;
    private boolean mainAuthor;
}
