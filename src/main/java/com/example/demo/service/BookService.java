package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

//    @Cacheable(cacheNames = "books")
    public List<Book> getBooks() {
        logger.info("Fetching all books");
        return bookRepository.findAll();
    }

//    @Cacheable(cacheNames = "books", key = "#id")
    public Optional<Book> getBooksById(Long id) {
        logger.info("Fetching book from db with id {}", id);
        return bookRepository.findById(id);
    }

//    @CachePut(cacheNames = "books", key = "#book.id")
    public Book addNewBook(Book book) {
        validations(book);
        logger.info("Book Added");
        return bookRepository.save(book);
    }


//    @CachePut(cacheNames = "books")
    @Transactional
    public void updateBook(Long id, String title, String author, Integer year, Integer isbn) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Book with id: " + id + " does not exist"
                ));

        if (title != null &&
                !title.isEmpty() &&
                !Objects.equals(book.getTitle(), title)) {
            book.setTitle(title);
        }

        if (author != null &&
                !author.isEmpty() &&
                !Objects.equals(book.getAuthor(), author)) {
            book.setAuthor(author);
        }

        if (year != null &&
                !Objects.equals(book.getYear(), year)) {
            book.setYear(year);
        }

        if (isbn != null &&
                !Objects.equals(book.getIsbn(), isbn)) {
            book.setIsbn(isbn);
        }
        logger.info("Book with id {} updated", id);
    }

//    @CacheEvict(cacheNames = "books", key = "#id")
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
        logger.info("Book with id {} deleted", id);
    }

    public void isEmpty(String field, String name) {
        if(field.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, name + " is required");
        }
    }

    public void validations(Book book) {
        isEmpty(book.getAuthor(), "Author");
        isEmpty(book.getTitle(), "Title");
        isEmpty(book.getYear().toString(), "Year");
        if(book.getYear() > LocalDate.now().getYear()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Year of publication is invalid");
        }
        isEmpty(book.getIsbn().toString(), "ISBN");
    }
}
