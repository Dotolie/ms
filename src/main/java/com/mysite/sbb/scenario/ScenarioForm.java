package com.mysite.sbb.scenario;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Setter
@Getter
public class ScenarioForm {
    private String name;
    private List<MultipartFile> files;
}
