package com.booksRest.books.services.impl;

import com.booksRest.books.domain.Book;
import com.booksRest.books.domain.BookEntity;
import com.booksRest.books.repositories.BookRepository;
import com.booksRest.books.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(final BookRepository bookRepository){

        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(final Book book) {
        final BookEntity bookEntity = bookToBookEntity(book);
        final BookEntity savedBookEntity = bookRepository.save(bookEntity);
        return bookEntityToBook(savedBookEntity);
    }

    @Override
    public Optional<Book> findById(String id) {
        final Optional<BookEntity> foundBook = bookRepository.findById(id);
        return foundBook.map(book -> bookEntityToBook(book));
    }

    @Override
    public List<Book> listBooks() {
        final List<BookEntity> foundBooks = bookRepository.findAll();
        return foundBooks.stream().map(book -> bookEntityToBook(book)).collect(Collectors.toList());
    }

    @Override
    public void deleteBookById(String isbn) {
        try {
            bookRepository.deleteById(isbn);
        }
        catch (EmptyResultDataAccessException ex){
            log.debug("Attempt to delete empty data"+ex);
        }
    }

    @Override
    public boolean isBookExists(Book book){
        return bookRepository.existsById(book.getIsbn());
    }


    private BookEntity bookToBookEntity(Book book){
        return BookEntity.builder()
                .isbn(book.getIsbn())
                .author(book.getAuthor())
                .title(book.getTitle())
                .build();
    }

    private Book bookEntityToBook(BookEntity bookEntity){
        return Book.builder()
                .isbn(bookEntity.getIsbn())
                .author(bookEntity.getAuthor())
                .title(bookEntity.getTitle())
                .build();
    }





}
