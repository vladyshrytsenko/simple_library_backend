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
public class BookToGenre extends Basic {

    @ManyToOne
    private Book book;

    @ManyToOne
    private Genre genre;
}
