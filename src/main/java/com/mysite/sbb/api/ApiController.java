package com.mysite.sbb.api;

import com.mysite.sbb.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ApiController {
    @GetMapping("/scenario")
    public String Scenario(Model model, Principal principal, @RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "file", defaultValue = "" ) String name) {
        Scenario scenario;

        System.out.println("-----------------------" + name);
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8000")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        if (name.isEmpty()) {
            scenario = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/scenario/")
                            .build())
                    .retrieve()
                    .bodyToMono(Scenario.class)
                    .block();

        }
        else {
            scenario = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/scenario/")
                            .queryParam("file", name)
                            .build())
                    .retrieve()
                    .bodyToMono(Scenario.class)
                    .block();

        }


        assert scenario != null;
        List<Procedure> procedures = scenario.getProcedure();

        PageRequest pageRequest = PageRequest.of(page, 20);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), procedures.size());

        System.out.println("start=" + start + ", end=" + end);



        Page<Procedure> paging = new PageImpl<>(procedures.subList(start, end), pageRequest, procedures.size());

        model.addAttribute("paging", paging);
        model.addAttribute("scenario", scenario.getScenario());
        model.addAttribute("name", principal.getName());

        System.out.println(scenario);

        return "/scenario_load";
    }
}
