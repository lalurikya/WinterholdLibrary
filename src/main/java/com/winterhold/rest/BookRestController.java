package com.winterhold.rest;

import com.winterhold.dto.book.InsertBookDTO;
import com.winterhold.dto.book.UpdateBookDTO;
import com.winterhold.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@CrossOrigin
@RestController
@RequestMapping("/api/book")
public class BookRestController {

    @Autowired
    private BookService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "") String name,
                         @RequestParam(defaultValue = "") String title,
                         @RequestParam(defaultValue = "") String authorName,
                         Model model){
        var rows = service.getRows(name, title, authorName, page);
        return ResponseEntity.status(200).body(rows);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Object> get(@PathVariable(required = true) String code){
        var dto = service.findOne(code);
        return ResponseEntity.status(200).body(dto);
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody InsertBookDTO dto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        service.insert(dto);
        return ResponseEntity.status(201).body("Berhasil insert");
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpdateBookDTO dto,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes) {
        service.update(dto);
        return ResponseEntity.status(200).body("Berhasil Update/Insert");
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) String code){
        service.delete(code);
        return ResponseEntity.status(200).body(code);
    }

    @GetMapping("/summary/{code}")
    public ResponseEntity<Object> getBookSummary(@PathVariable(required = true) String code){
        try {
            var dto = service.getInfo(code);
            return ResponseEntity.status(200).body(dto);
        }catch (Exception e){
            return ResponseEntity.status(500).body("Error");
        }
    }
}
