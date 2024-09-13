package com.mysite.sbb.card;

import com.mysite.sbb.question.QuestionForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping("/card")
@RequiredArgsConstructor
@Controller
public class CardController {

    private final CardService cardService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String list( Model model, Principal principal, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        log.info("page:{}, kw:{}", page, kw);

        Page<Card> paging = this.cardService.getList(page, kw);

        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        model.addAttribute("name", principal.getName());


        return "card_list";
    }


    @GetMapping("/list2")
    public String list2(Model model, Principal principal, @RequestParam(value = "kw", defaultValue = "") String kw) {
        model.addAttribute("kw", kw);
        model.addAttribute("name", principal.getName());
        return "card_list2";
    }


    @PostMapping("/list2")
    public String response(Model model, @ModelAttribute(value = "kw") String kw, @ModelAttribute(value="page") Integer page) {
        log.info("kw {}, page {}", kw,page);
        Page<Card> paging = this.cardService.getList(page, kw);

        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);

        return "card_table";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String cardCreate(CardForm cardFormForm) {
        return "card_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String cardCreate(@Valid CardForm cardForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            log.info("err=" + bindingResult.getGlobalError());
            return "card_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());

        Card card = new Card();
        card.setPartName(cardForm.getPartName());
        card.setPartCode(cardForm.getPartCode());
        card.setSerialNumber(cardForm.getSerialNumber());
        card.setCompCabinet(cardForm.getCompCabinet());
        card.setCompLocation(cardForm.getCompLocation());
        card.setCompRack(cardForm.getCompRack());
        card.setCompSlot(cardForm.getCompSlot());
        card.setAuthor(siteUser);
        card.setCreateDate(LocalDateTime.now());

        this.cardService.create(card);

        return "redirect:/card/list";
    }
}
