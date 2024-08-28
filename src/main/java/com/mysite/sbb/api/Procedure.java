package com.mysite.sbb.api;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Data
public class Procedure {
    private Integer id;
    private String title;
    private List<Integer> setValue;
    private List<String> checkValue;

}
