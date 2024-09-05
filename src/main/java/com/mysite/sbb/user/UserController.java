package com.mysite.sbb.user;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionForm;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}

	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "signup_form";
		}

		if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
			return "signup_form";
		}

		try {
			this.userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1(), userCreateForm.getUserRole());
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			return "signup_form";
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "signup_form";
		}

		return "redirect:/user/list";
	}

	@GetMapping("/login")
	public String login() {
		return "login_form";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/list")
	public String list(Model model, Principal principal, @RequestParam(value = "page", defaultValue = "0") int page,
					   @RequestParam(value = "kw", defaultValue = "") String kw) {
		Page<SiteUser> paging = this.userService.getList(page, kw);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		model.addAttribute("act", "user");
		model.addAttribute("name", principal.getName());
		return "user_list";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, UserCreateForm userCreateForm, @PathVariable("id") Long id) {
		SiteUser siteUser = this.userService.getUser(id);
		model.addAttribute("siteUser", siteUser);
		return "user_detail";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String userModify(UserCreateForm userCreateForm, @PathVariable("id") Long id, Principal principal) {
		SiteUser siteUser = this.userService.getUser(id);
		if (!siteUser.getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		userCreateForm.setEmail(siteUser.getEmail());
		userCreateForm.setUsername(siteUser.getUsername());
		userCreateForm.setCreateDate(siteUser.getCreateDate());
		userCreateForm.setUserRole(siteUser.getUserRole());
		return "user_form";
	}


	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String userModify(@Valid UserCreateForm userCreateForm, BindingResult bindingResult, Principal principal,
								 @PathVariable("id") Long id) {
		if (bindingResult.hasErrors()) {
			bindingResult.getFieldError("password1");
			return "user_form";
		}
		SiteUser siteUser = this.userService.getUser(id);
		if (siteUser.getUserRole() == UserRole.USER ) {
			if (!siteUser.getUsername().equals(principal.getName())) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
			}
		}
		this.userService.modify(siteUser, userCreateForm.getEmail(), userCreateForm.getPassword1(), userCreateForm.getUserRole());
		return String.format("redirect:/user/list");
	}

	@ExceptionHandler(value = ResponseStatusException.class)
	public String responseStatusException(ResponseStatusException e) {
		return "404";
	}
}
