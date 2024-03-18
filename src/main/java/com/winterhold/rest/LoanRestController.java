package com.winterhold.rest;

import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.dto.loan.UpsertLoanDTO;
import com.winterhold.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/loan")
public class LoanRestController {

    @Autowired
    private LoanService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "") String title,
                                @RequestParam(defaultValue = "") String customer){
        var rows = service.getRows(title, customer, page);
        return ResponseEntity.status(200).body(rows);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable(required = false) Long id){
        var dto = service.findOne(id);
        return ResponseEntity.status(200).body(dto);
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertLoanDTO dto, BindingResult bindingResult) {
        service.insert(dto);
        return ResponseEntity.status(201).body("Berhasil insert");
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertLoanDTO dto, BindingResult bindingResult) {
        service.update(dto);
        return ResponseEntity.status(200).body("Berhasil insert/update");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Long id){
        service.delete(id);
        return ResponseEntity.status(200).body(id);
    }
}
