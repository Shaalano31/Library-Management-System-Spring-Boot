package com.example.demo.patron;

import com.example.demo.book.Book;
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
class PatronServiceTest {

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private PatronService patronService;

    @Test
    void getPatrons() {
        Patron patron = new Patron("Khaled", 1000, "test@gmail.com");
        Patron patron2 = new Patron("Khaled2", 1200, "test2@gmail.com");

        ResponseEntity savedPatron = patronService.addNewPatron(patron);
        ResponseEntity savedPatron2 = patronService.addNewPatron(patron2);

        ResponseEntity<List<Patron>> savePatron = patronService.getPatrons();

        assertThat(savePatron).isNotNull();
    }

    @Test
    void getPatronById() {
        Patron patron = new Patron("Khaled", 1000, "test@gmail.com");

        when(patronRepository.findById(1L)).thenReturn(Optional.ofNullable(patron));

        ResponseEntity savedPatron = patronService.getPatronById(1L);

        assertThat(savedPatron).isNotNull();
    }

    @Test
    void addNewPatron() {
        Patron patron = new Patron("Khaled", 1000, "test@gmail.com");

        when(patronRepository.save(Mockito.any(Patron.class))).thenReturn(patron);

        ResponseEntity savedPatron = patronService.addNewPatron(patron);

        assertThat(savedPatron).isNotNull();
    }

    @Test
    void deletePatron() {
        Patron patron = new Patron("Khaled", 1000, "test@gmail.com");

        when(patronRepository.findById(1L)).thenReturn(Optional.ofNullable(patron));

        ResponseEntity savedPatron = patronService.addNewPatron(patron);
        ResponseEntity savedPatron2 = patronService.getPatronById(1L);

        assertAll(() -> patronService.deletePatron(1L));
    }
}