package com.mysite.sbb.scenario;

import com.mysite.sbb.card.Card;
import com.mysite.sbb.user.SiteUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Slf4j
@RequestMapping("/test")
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

        return "redirect:/test/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String list(Model model, Principal principal, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        log.info("page:{}, kw:{}", page, kw);

        Page<Scenario> paging = this.scenarioService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        model.addAttribute("name", principal.getName());


        return "scenario_list";
    }
}
