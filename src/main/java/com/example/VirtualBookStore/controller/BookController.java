package com.example.VirtualBookStore.controller;

import com.example.VirtualBookStore.model.Book;
import com.example.VirtualBookStore.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // Raw JSON response from Airtable (for debugging or inspection)
    @GetMapping("/raw")
    public ResponseEntity<String> getBooksRaw() {
        String json = bookService.getBooksJson();
        return ResponseEntity.ok(json);
    }

    // Structured list of Book objects parsed from Airtable
    @GetMapping("/structured")
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    // Search for books by title, author, or genre
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String query) {
        List<Book> results = bookService.searchBooks(query);
        
        if (results == null) {
            return ResponseEntity.notFound().build(); // sends 404 response
        }

        return ResponseEntity.ok(results);
    }
    
    @GetMapping("/page")
    public ResponseEntity<List<Book>> getBooksByPage(@RequestParam int page, @RequestParam(defaultValue = "15") int size) {
        List<Book> allBooks = bookService.getBooks();
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, allBooks.size());

        if (fromIndex >= allBooks.size()) {
            return ResponseEntity.ok(List.of()); // empty page
        }

        return ResponseEntity.ok(allBooks.subList(fromIndex, toIndex));
    }


}