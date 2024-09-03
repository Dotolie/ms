package com.mysite.sbb;

import com.mysite.sbb.card.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/sbb")
	@ResponseBody
	public String index() {
		return "안녕하세요 sbb에 오신것을 환영합니다.";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/")
	public String root(@AuthenticationPrincipal UserDetails userDetails, Model model) {

		model.addAttribute("act", "dash");
		model.addAttribute("name", userDetails.getUsername());
		return "/index";
	}
}
