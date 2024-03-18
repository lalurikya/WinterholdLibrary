package com.winterhold.rest;

import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/author")
public class AuthorRestController{
    @Autowired
    private AuthorService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "") String name){
        var rows = service.getRows(name, page);
        return ResponseEntity.status(HttpStatus.OK).body(rows);//response body
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable(required = false) Long id){
        var dto = service.findOne(id);
        return ResponseEntity.status(200).body(dto);
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertAuthorDTO dto, BindingResult bindingResult) {
        service.save(dto);
        return ResponseEntity.status(201).body("Berhasil insert");
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertAuthorDTO dto, BindingResult bindingResult) {
        service.save(dto);
        return ResponseEntity.status(200).body("Berhasil insert/update");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Long id){
        service.delete(id);
        return ResponseEntity.status(200).body(id);
    }
}
