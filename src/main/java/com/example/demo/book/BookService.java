package com.example.demo.book;

import com.example.demo.patron.Patron;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void addNewBook(Book book) {
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
}
