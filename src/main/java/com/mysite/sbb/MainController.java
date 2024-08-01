package com.mysite.sbb;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

	@GetMapping("/sbb")
	@ResponseBody
	public String index() {
		return "안녕하세요 sbb에 오신것을 환영합니다.";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/")
	public String root(Model model) {
		model.addAttribute("act", "dash");
		return "/index";
	}
}
