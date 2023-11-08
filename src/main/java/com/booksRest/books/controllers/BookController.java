package com.booksRest.books.controllers;

import com.booksRest.books.domain.Book;
import com.booksRest.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> createBook(@PathVariable final String isbn, @RequestBody final Book book){
        book.setIsbn(isbn);

        final boolean isBookExists = bookService.isBookExists(book);
        final Book savedBook = bookService.save(book);

        if(isBookExists) return new ResponseEntity<Book>(savedBook, HttpStatus.OK);
        else {
            ResponseEntity<Book> response = new ResponseEntity<>(savedBook, HttpStatus.CREATED);
            return response;
        }
    }

    @GetMapping(path = "books/{isbn}")
    public ResponseEntity<Book> retrieveBook(@PathVariable final String isbn){
        final Optional<Book> foundBook = bookService.findById(isbn);
        return foundBook
                .map(book -> new ResponseEntity<Book>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<Book>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/books")
    public ResponseEntity<List<Book>> listBooks(){
        return new ResponseEntity<List<Book>>(bookService.listBooks(),HttpStatus.OK);
    }

    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity deleteBook(@PathVariable final String isbn) {
        bookService.deleteBookById(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
