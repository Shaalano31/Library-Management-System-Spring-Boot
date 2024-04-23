package com.example.demo.book;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void getBooks() {
        Book book = new Book("Khaled", "Khaled2", 2000, 2037);
        Book book2 = new Book("Khaled", "Khaled2", 2000, 2037);

        ResponseEntity savedBook = bookService.addNewBook(book);
        ResponseEntity savedBook2 = bookService.addNewBook(book2);

        ResponseEntity<List<Book>> saveBook = bookService.getBooks();

        assertThat(saveBook).isNotNull();
    }

    @Test
    public void getBooksById() {
        Book book = new Book("Khaled", "Khaled2", 2000, 2037);

        when(bookRepository.findById(1L)).thenReturn(Optional.ofNullable(book));

        ResponseEntity savedBook = bookService.getBooksById(1L);

        assertThat(savedBook).isNotNull();
    }

    @Test
    public void addNewBook() {
        Book book = new Book("Khaled", "Khaled2", 2000, 2037);

        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book);

        ResponseEntity savedBook = bookService.addNewBook(book);

        assertThat(savedBook).isNotNull();
    }

    @Test
    public void deleteBook() {
        Book book = new Book("Khaled", "Khaled2", 2000, 2037);

        when(bookRepository.findById(1L)).thenReturn(Optional.ofNullable(book));

        ResponseEntity savedBook = bookService.addNewBook(book);
        ResponseEntity savedBook2 = bookService.getBooksById(1L);

        assertAll(() -> bookService.deleteBook(1L));
    }
}