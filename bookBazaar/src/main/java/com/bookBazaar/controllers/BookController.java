package com.bookBazaar.controllers;

import com.bookBazaar.models.dto.BookDTO;
import com.bookBazaar.services.interfaces.BookServiceINT;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {})
public class BookController {

    private final BookServiceINT bookService;

    @PostMapping("/new")
    public ResponseEntity<BookDTO> createNewBook(@RequestBody BookDTO bookRequest)
    {
        BookDTO response = bookService.createNewBook(bookRequest);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookDTO>> getAllBooks()
    {
        List<BookDTO> response = bookService.getAllBooks();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDTO> getSpecificBook(@PathVariable(value="id") final String id)
    {
        BookDTO response = bookService.getSpecificBookByID(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/find/{kind}")
    public ResponseEntity<List<BookDTO>> getByKind(@PathVariable(value="kind") final String kind, @RequestBody String value)
    {
        List<BookDTO> response = bookService.getAllBooksByKind(kind, value);
        return ResponseEntity.ok().body(response);
    }
}