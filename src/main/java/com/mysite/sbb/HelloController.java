package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
		return "tables";
	}
}
