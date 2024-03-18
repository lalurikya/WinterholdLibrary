package com.winterhold.controller;

import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorService service;

    @GetMapping("/index")//ini buat url/routing method nya
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String name,
                        Model model){
        var rows = service.getRows(name, page);
        model.addAttribute("grid", rows);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", rows.getTotalPages());
        model.addAttribute("name", name);
        return "author/author-index";//ini buat directory
    }

    @GetMapping("/upsertForm")
    public String upsertForm(@RequestParam(required = false) Long id, Model model){
        var dto = new UpsertAuthorDTO();
        if(id != null){
            dto = service.findOne(id);
        }
        model.addAttribute("dto", dto);
        return "author/author-form";
    }

    @PostMapping("/upsert")
    public String upsert(@Valid @ModelAttribute("dto") UpsertAuthorDTO dto, BindingResult bindingResult, Model model){

        if(!bindingResult.hasErrors()){
            service.save(dto);
            return "redirect:/author/index";
        }
        return "author/author-form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long id, Model model){
        var depedencies = service.countBookByAuthor(id);
        if(depedencies == 0){
            service.delete(id);
            return "redirect:/author/index";
        }
        model.addAttribute("depedencies", depedencies);
        return "author/author-delete";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(required = true) Long id,
                         Model model){
        var rows = service.getDetails(id, page);
        var header = service.getHeaderInfo(id);

        model.addAttribute("grid", rows);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", rows.getTotalPages());
        model.addAttribute("header", header);
        return "author/author-detail";
    }

}
