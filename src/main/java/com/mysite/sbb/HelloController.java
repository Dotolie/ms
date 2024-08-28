package com.mysite.sbb;

import com.mysite.sbb.api.Scenario;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
public class HelloController {
	@GetMapping("/hello")
	@ResponseBody
	public String hello() {
		return "Hello Spring Boot Board";
	}

	@GetMapping("/list2")
	public String list2(Model model) {
		model.addAttribute("act", "table");
		return "idx";
	}

	@GetMapping("/sc")
	public String doTest(Model model) {
		String code ="test.xlsx";


		WebClient webClient = WebClient.builder()
				.baseUrl("http://localhost:8000")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();


		Scenario scenario = webClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/scenario/")
						.queryParam("file", code)
						.build())
				.retrieve()
				.bodyToMono(Scenario.class)
				.block();


		System.out.println(scenario);

		return "/index";
	}

}
