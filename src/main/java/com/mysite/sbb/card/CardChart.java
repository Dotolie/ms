package com.mysite.sbb.card;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class CardChart {
    private String name;
    private List<String> label;
    private List<Long> data;

}
