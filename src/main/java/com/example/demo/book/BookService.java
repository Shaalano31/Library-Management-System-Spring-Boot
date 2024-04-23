package com.example.demo.book;

import com.example.demo.patron.Patron;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public ResponseEntity<List<Book>> getBooks() {
        List<Book> results =  bookRepository.findAll();
        if(results.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        else {
            return ResponseEntity.ok(results);
        }
    }

    public ResponseEntity<Optional<Book>> getBooksById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return ResponseEntity.ok(book);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    public void addNewBook(Book book) {
        isEmpty(book.getAuthor(), "Author");
        isEmpty(book.getTitle(), "Title");
        isEmpty(book.getYear().toString(), "Year");
        if(book.getYear() > LocalDate.now().getYear()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Year of publication is invalid");
        }
        isEmpty(book.getIsbn().toString(), "ISBN");

        bookRepository.save(book);
    }

    @Transactional
    public void updateBook(Long id, String title, String author, Integer year, Integer isbn) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Book with id: " + id + " does not exist"
                ));

        book.setTitle(title);
        book.setAuthor(author);
        book.setYear(year);
        book.setIsbn(isbn);
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
}
