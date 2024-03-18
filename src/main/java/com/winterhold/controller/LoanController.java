package com.winterhold.controller;

import com.winterhold.dto.loan.UpsertLoanDTO;
import com.winterhold.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    private LoanService service;

    @GetMapping("/index")//ini buat url/routing method nya
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String title,
                        @RequestParam(defaultValue = "") String customer,
                        Model model){
        var rows = service.getRows(title, customer, page);
        model.addAttribute("grid", rows);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", rows.getTotalPages());
        model.addAttribute("customerName", customer);
        model.addAttribute("bookTitle", title);
        return "loan/loan-index";
    }

    @GetMapping("/return")
    public String extend(Long id) {
        service.returnBook(id);
        return "redirect:/loan/index";
    }

    @GetMapping("/upsertForm")
    public String upsertForm(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            var find = service.findOne(id);
            model.addAttribute("bookDropdown", service.getBookDropdown());
            model.addAttribute("customerDropdown", service.getCustomerDropdown());
            model.addAttribute("dto", find);
            return "loan/loan-form";
        } else {
            var dto = new UpsertLoanDTO();
            model.addAttribute("bookDropdown", service.getBookDropdown());
            model.addAttribute("customerDropdown", service.getCustomerDropdown());
            model.addAttribute("dto", dto);
        }
        return "loan/loan-form";
    }

    @PostMapping("/upsert")
    public String upsert(@Valid @ModelAttribute("dto") UpsertLoanDTO dto,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bookDropdown", service.getBookDropdown());
            model.addAttribute("customerDropdown", service.getCustomerDropdown());
            return "loan/loan-form";
        }
        service.insert(dto);
        return "redirect:/loan/index";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("dto") UpsertLoanDTO dto, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes , Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("bookDropdown", service.getBookDropdown());
            model.addAttribute("customerDropdown", service.getCustomerDropdown());
            return "loan/loan-form";
        }
        service.update(dto);
        return "redirect:/loan/index";
    }
}
