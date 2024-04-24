package com.example.demo.controller;

import com.example.demo.service.BorrowingService;
import com.example.demo.model.Borrowing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
public class BorrowingController {

    private final BorrowingService borrowingService;

    @Autowired
    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @PostMapping("borrow/{bookId}/patron/{patronId}")
    public void borrowBook(@RequestBody Borrowing borrowing) {
        borrowingService.borrowNewBook(borrowing);
    }

    @PutMapping("return/{bookId}/patron/{patronId}")
    public void returnBook(@RequestParam Long id) {
        borrowingService.returnBook(id);
    }
}
