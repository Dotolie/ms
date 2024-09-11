package com.mysite.sbb.scenario;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Scenario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String orgNm;
    private String savedNm;
    private String savedPath;
    private LocalDateTime createDate;

    @Builder
    public Scenario(Long id, String orgNm, String savedNm, String savedPath, LocalDateTime createDate) {
        this.id = id;
        this.orgNm = orgNm;
        this.savedNm = savedNm;
        this.savedPath = savedPath;
        this.createDate = createDate;
    }

    public Scenario() {

    }
}
