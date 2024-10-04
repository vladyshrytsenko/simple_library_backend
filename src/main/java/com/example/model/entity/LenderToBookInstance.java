package com.example.model.entity;

import com.example.model.Basic;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.OffsetDateTime;

@Entity
@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@Where(clause = "soft_delete = false")
public class LenderToBookInstance extends Basic {

    @ManyToOne
    private Lender lender;

    @ManyToOne
    private BookInstance bookInstance;

    private OffsetDateTime lent;
    private OffsetDateTime dueBack;
    private OffsetDateTime returned;

}
