package com.winterhold.rest;

import com.winterhold.dto.category.UpsertCategoryDTO;
import com.winterhold.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/category")
public class CategoryRestController {
    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "") String name){
        var rows = service.getRows(name, page);
        return ResponseEntity.status(HttpStatus.OK).body(rows);//response body
    }

    @GetMapping("/{name}")
    public ResponseEntity<Object> get(@PathVariable(required = false) String name){
        var dto = service.findOne(name);
        return ResponseEntity.status(200).body(dto);
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertCategoryDTO dto, BindingResult bindingResult) {
        service.save(dto);
        return ResponseEntity.status(201).body("Berhasil insert");
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertCategoryDTO dto, BindingResult bindingResult) {
        service.save(dto);
        return ResponseEntity.status(200).body("Berhasil insert/update");
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) String name){
        service.delete(name);
        return ResponseEntity.status(200).body(name);
    }
}
