package com.booksRest.books.services;

import com.booksRest.books.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    boolean isBookExists(Book book);
    Book save(Book book);

    Optional<Book> findById(String id);

    List<Book> listBooks();

    void deleteBookById(String isbn);
}
