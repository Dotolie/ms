package com.mysite.sbb.result;

import com.mysite.sbb.card.Card;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String type;

    private boolean result;

    private LocalDateTime testDate;
    @ManyToOne
    private Card card;
}
