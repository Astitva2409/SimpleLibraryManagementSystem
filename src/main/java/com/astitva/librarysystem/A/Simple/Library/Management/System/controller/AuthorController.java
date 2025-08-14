package com.astitva.librarysystem.A.Simple.Library.Management.System.controller;

import com.astitva.librarysystem.A.Simple.Library.Management.System.dto.AuthorDTO;
import com.astitva.librarysystem.A.Simple.Library.Management.System.exceptions.ResourceNotFoundException;
import com.astitva.librarysystem.A.Simple.Library.Management.System.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/authors")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorDTO> createNewAuthor(@RequestBody AuthorDTO inputAuthor) {
        AuthorDTO authorDTO = authorService.createNewAuthor(inputAuthor);
        return new ResponseEntity<>(authorDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        AuthorDTO authorDTO = authorService.getAuthorById(id);
        return ResponseEntity.ok(authorDTO);
    }

    @GetMapping(path = "/searchByName")
    public ResponseEntity<List<AuthorDTO>> getAuthorByName(@RequestParam String name) {
        return ResponseEntity.ok(authorService.getAuthorByName(name));
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Boolean> deleteAuthorById(@PathVariable Long id) {
        boolean isDeleted = authorService.deleteAuthorById(id);
//        if (!isDeleted) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(isDeleted);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AuthorDTO> updateAuthorById(@RequestBody Map<String, Object> updates,
                                                      @PathVariable Long id) {
        AuthorDTO authorDTO = authorService.updateAuthorById(updates, id);
        return ResponseEntity.ok(authorDTO);
    }
}
