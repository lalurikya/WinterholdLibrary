package com.winterhold.rest;

import com.winterhold.dto.customer.InsertCustomerDTO;
import com.winterhold.dto.customer.UpdateCustomerDTO;
import com.winterhold.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {
    @Autowired
    private CustomerService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "") String name,
                                      @RequestParam(defaultValue = "") String membershipNumber){
        var rows = service.getRows(membershipNumber, name, page);
        return ResponseEntity.status(200).body(rows);
    }

    @GetMapping("/{membershipNumber}")
    public ResponseEntity<Object> get(@PathVariable(required = true) String membershipNumber){
        var dto = service.findOne(membershipNumber);
        return ResponseEntity.status(200).body(dto);
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody InsertCustomerDTO dto, BindingResult bindingResult){
        service.insert(dto);
        return ResponseEntity.status(201).body("Berhasil insert");
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpdateCustomerDTO dto, BindingResult bindingResult){
        service.update(dto);
        return ResponseEntity.status(200).body("Berhasil Update/Insert");
    }

    @DeleteMapping("/{membershipNumber}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) String membershipNumber){
        service.delete(membershipNumber);
        return ResponseEntity.status(200).body(membershipNumber);
    }

    @GetMapping("/info/{membershipNumber}")
    public ResponseEntity<Object> summary(@PathVariable(required = true) String membershipNumber) {
        var dto = service.getInfo(membershipNumber);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
