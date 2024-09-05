package com.mysite.sbb.scenario;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ScenarioController {


    private final ScenarioService scenarioService;

    @GetMapping("/upload")
    public String getFileForm(ScenarioForm scenarioForm) {
        return "scenario_upload";
    }

    @PostMapping("/upload")
    public String uploadScenarioFile(@Valid ScenarioForm scenarioForm, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            bindingResult.reject("signupFailed", "이미 등록된 사용");
            return "scenario_upload";
        }

        for ( MultipartFile multipartFile : scenarioForm.getFiles()) {
            try {
                this.scenarioService.saveFile(multipartFile);
            } catch (DataIntegrityViolationException e) {
                e.printStackTrace();
                bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
                return "scenario_upload";
                }
        }

        return "redirect:/";
    }
}
