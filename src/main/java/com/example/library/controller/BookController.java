package com.example.library.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.library.model.Book;

@RestController
@RequestMapping("/books")
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController() {
        books.add(new Book(1L, "Java Basics", "James"));
        books.add(new Book(2L, "Spring Boot", "John"));
    }

    @GetMapping
    public List<Book> getBooks() {
        return books;
    }

    @PostMapping
    public String addBook(@RequestBody Book book) {
        books.add(book);
        return "Book added successfully";
    }

    @PutMapping("/{id}")
    public String updateBook(
            @PathVariable Long id,
            @RequestBody Book updatedBook) {

        for (Book book : books) {
            if (book.getId().equals(id)) {
                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                return "Book updated successfully";
            }
        }

        return "Book not found";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {

        books.removeIf(book -> book.getId().equals(id));

        return "Book deleted successfully";
    }
}