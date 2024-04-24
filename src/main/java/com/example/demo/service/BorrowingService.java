package com.example.demo.service;

import com.example.demo.model.Borrowing;
import com.example.demo.repository.BorrowingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BorrowingService {

    private final BorrowingRepository borrowingRepository;

    public BorrowingService(BorrowingRepository borrowingRepository) {
        this.borrowingRepository = borrowingRepository;
    }

    public void borrowNewBook(Borrowing borrowing) {
        borrowing.setBorrow_date(LocalDate.now());
        borrowingRepository.save(borrowing);
    }

    public void returnBook(Long id) {
        Borrowing borrowing = borrowingRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Borrowing of the book with id: " + id + " does not exist"
                ));

        borrowing.setReturn_date(LocalDate.now());
        borrowingRepository.save(borrowing);
    }
}
