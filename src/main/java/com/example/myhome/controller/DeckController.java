package com.example.myhome.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/deck")
public class DeckController {

    @GetMapping("/deckList")
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText) {
//
//        model.addAttribute("startPage"  , startPage);
//        model.addAttribute("endPage"    , endPage);
//        model.addAttribute("boards"     , boards);
        System.out.println("deck 호출");
        System.out.println("deck 호출");
        System.out.println("deck 호출");
        System.out.println("deck 호출");
        System.out.println("deck 호출");
        System.out.println("deck 호출");
        System.out.println("deck 호출");
        System.out.println("deck 호출");
        return "deck/deckList";
    }


}
