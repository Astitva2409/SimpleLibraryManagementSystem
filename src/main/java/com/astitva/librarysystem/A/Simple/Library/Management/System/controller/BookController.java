package com.astitva.librarysystem.A.Simple.Library.Management.System.controller;

import com.astitva.librarysystem.A.Simple.Library.Management.System.dto.BookDTO;
import com.astitva.librarysystem.A.Simple.Library.Management.System.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDTO> createNewBook(@RequestBody @Valid BookDTO inputBook) {
        BookDTO bookDTO = bookService.createNewBook(inputBook);
        return new ResponseEntity<>(bookDTO, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks(@RequestParam(defaultValue = "id") String sortBy,
                                                     @RequestParam(defaultValue = "0") Integer pageNumber) {
        return ResponseEntity.ok(bookService.getAllBooks(sortBy, pageNumber));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO bookDTO = bookService.getBookById(id);
        return ResponseEntity.ok(bookDTO);
    }

    @GetMapping(path = "/searchByTitle")
    public ResponseEntity<List<BookDTO>> getBooksByTitle(@RequestParam String title,
                                                         @RequestParam(defaultValue = "id") String sortBy,
                                                         @RequestParam(defaultValue = "0") Integer pageNumber) {
        return ResponseEntity.ok(bookService.getBooksByTitle(title, sortBy, pageNumber));
    }

    @GetMapping(path = "/searchBooksAfterDate")
    public ResponseEntity<List<BookDTO>> getAllBooksPublishedAfterDate(@RequestParam LocalDate date) {
        return ResponseEntity.ok(bookService.getAllBooksPublishedAfterDate(date));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteBookById(@PathVariable Long id) {
        boolean isDeleted = bookService.deleteBookById(id);
        return ResponseEntity.ok(isDeleted);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BookDTO> updateBookById(@RequestBody Map<String, Object> updates,
                                                  @PathVariable Long id) {
        BookDTO bookDTO = bookService.updateBookById(updates, id);
        return ResponseEntity.ok(bookDTO);
    }
}
