package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBooksById(Long id) {
        return bookRepository.findById(id);
    }

    public Book addNewBook(Book book) {
        validations(book);
        return bookRepository.save(book);
    }

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
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
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
