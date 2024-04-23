package com.example.demo.borrowing;

import com.example.demo.book.Book;
import com.example.demo.patron.Patron;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class Borrowing {

    @Id
    @SequenceGenerator(
            name = "borrowing_sequence",
            sequenceName = "borrowing_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "borrowing_sequence"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "patron_id")
    private Patron patron;

    private LocalDate borrow_date;
    private LocalDate return_date;

    public Borrowing() {
    }

    public Borrowing(Long id, Book book, Patron patron) {
        this.id = id;
        this.book = book;
        this.patron = patron;
    }

    public Borrowing(Book book, Patron patron) {
        this.book = book;
        this.patron = patron;
    }

    @Override
    public String toString() {
        return "Borrowing{" +
                "id=" + id +
                ", book=" + book +
                ", patron=" + patron +
                ", borrow_date=" + borrow_date +
                ", return_date=" + return_date +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public LocalDate getBorrow_date() {
        return borrow_date;
    }

    public void setBorrow_date(LocalDate borrow_date) {
        this.borrow_date = borrow_date;
    }

    public LocalDate getReturn_date() {
        return return_date;
    }

    public void setReturn_date(LocalDate return_date) {
        this.return_date = return_date;
    }
}
