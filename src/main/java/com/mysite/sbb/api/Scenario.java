package com.mysite.sbb.api;

import com.mysite.sbb.api.Procedure;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Data
public class Scenario {
    private String file;
    private String scenario;
    private String author;
    private String date;
    private Integer totals;
    private Integer setSize;
    private List<String> setList;
    private Integer checkSize;
    private List<String> checkList;
    private List<Procedure> procedure;
//    private String procedure;

}
