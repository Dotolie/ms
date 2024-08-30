package com.mysite.sbb.card;

import com.mysite.sbb.result.Result;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String partName;
    private String partCode;
    private String serialNumber;
    private String compCabinet;

    private Integer compLocation;
    private Integer compRack;
    private Integer compSlot;

    private LocalDateTime createDate;

    @ManyToOne
    private SiteUser author;

    @OneToMany
    private List<Result> ResultList;
}
