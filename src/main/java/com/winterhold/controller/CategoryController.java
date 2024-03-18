package com.winterhold.controller;

import com.winterhold.dto.book.InsertBookDTO;
import com.winterhold.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String name,
                        Model model){
        var rows = service.getRows(name, page);
        model.addAttribute("grid", rows);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", rows.getTotalPages());
        model.addAttribute("name", name);
        return "category/category-index";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) String name, Model model){
        var depedencies = service.countBookByCategory(name);
        if(depedencies == 0){
            service.delete(name);
            return "redirect:/category/index";
        }
        model.addAttribute("depedencies", depedencies);
        return "category/category-delete";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(required = true) String name,
                        @RequestParam(defaultValue = "") String title,
                        @RequestParam(defaultValue = "") String authorName,
                        Model model){
        var rows = service.getDetails(name, title, authorName, page);
        var category = service.findOne(name);

        model.addAttribute("grid", rows);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", rows.getTotalPages());
        model.addAttribute("name", name);
        model.addAttribute("authorName", authorName);
        model.addAttribute("title", title);
        model.addAttribute("category", category);
        return "category/category-detail";
    }

    @GetMapping("/upsertDetailForm")
    public String upsertDetailForm(@RequestParam(required = false) String code,
                                   @RequestParam(required = true) String categoryName,
                                   Model model){
        if(code != null){
            var dto = service.findOneBook(code);
            model.addAttribute("dto", dto);
            model.addAttribute("authorDropdown", service.getAuthorDropdown());
            return "category/category-detail-update-form";
        }else{
            var dto = new InsertBookDTO();
            dto.setCategoryName(categoryName);
            model.addAttribute("dto", dto);
            model.addAttribute("authorDropdown", service.getAuthorDropdown());
            return "category/category-detail-insert-form";
        }
    }

    @PostMapping("/upsertDetail")
    public String insert(@Valid @ModelAttribute("dto") InsertBookDTO dto,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authorDropdown", service.getAuthorDropdown());
            return "category/category-detail-insert-form";
        }
        service.insertBook(dto);
        redirectAttributes.addAttribute("name",dto.getCategoryName());
        return "redirect:/category/detail";
    }

    @GetMapping("/deleteDetail")
    public String deleteDetail(@RequestParam(required = true) String code, String name, Model model, RedirectAttributes redirectAttributes){
        var depedencies = service.countLoanByBook(code);
        if(depedencies == 0){
            service.deleteBook(code);
            redirectAttributes.addAttribute("name", name);
            return "redirect:/category/detail";
        }
        model.addAttribute("depedencies", depedencies);
        model.addAttribute("name", name);
        return "category/category-detail-delete";
    }


}
