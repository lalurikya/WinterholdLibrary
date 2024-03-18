package com.winterhold.controller;

import com.winterhold.dao.LoanRepository;
import com.winterhold.dto.customer.InsertCustomerDTO;
import com.winterhold.dto.customer.UpdateCustomerDTO;
import com.winterhold.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;


    //Action Method
    @GetMapping("/index")//ini buat url/routing method nya
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "") String membershipNumber,
                        @RequestParam(defaultValue = "") String name,
                        Model model){
        var rows = service.getRows(membershipNumber, name, page);
        model.addAttribute("grid", rows);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", rows.getTotalPages());
        model.addAttribute("membershipNumber", membershipNumber);
        model.addAttribute("name", name);
        return "customer/customer-index";//ini buat directory
    }

    @GetMapping("/upsertForm")
    public String upsertForm(@RequestParam(required = false) String membershipNumber,
                             Model model){
        if(membershipNumber != null){
            var dto = service.findOne(membershipNumber);
            model.addAttribute("dto", dto);
            model.addAttribute("type", "update");
        }else{
            var dto = new InsertCustomerDTO();
            model.addAttribute("dto", dto);
            model.addAttribute("type", "insert");
        }
        return "customer/customer-form";
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("dto") InsertCustomerDTO dto,
                         BindingResult bindingResult,
                         Model model){
        if(!bindingResult.hasErrors()){
            service.insert(dto);
            return "redirect:/customer/index";
        }
        model.addAttribute("type", "insert");
        return "customer/customer-form";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("dto") UpdateCustomerDTO dto,
                         BindingResult bindingResult,
                         Model model){

        if(!bindingResult.hasErrors()){
            service.update(dto);
            return "redirect:/customer/index";
        }
        model.addAttribute("type", "Update");
        return "customer/customer-form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) String membershipNumber, String name, Model model){
        var depedencies = service.countLoanByCustomer(membershipNumber);
        if(depedencies == 0){
            service.delete(membershipNumber);
            return "redirect:/customer/index";
        }
        model.addAttribute("depedencies", depedencies);
        model.addAttribute("name", name);
        return "customer/customer-delete";
    }

    @GetMapping("/extend")
    public String extend(String number) {
        service.extendMembershipExpireDate(number);
        return "redirect:/customer/index";
    }
}
