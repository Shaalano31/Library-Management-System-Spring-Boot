package com.example.demo.service;

import com.example.demo.model.Patron;
import com.example.demo.repository.PatronRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PatronService {

    private final PatronRepository patronRepository;

    @Autowired
    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public List<Patron> getPatrons() {
        return patronRepository.findAll();
    }

    public Optional<Patron> getPatronById(Long id) {
        return patronRepository.findById(id);
    }

    public Patron addNewPatron(@RequestBody Patron patron) {

        isEmpty(patron.getEmail(), "Email");
        uniqueEmail(patron.getEmail());
        isEmpty(patron.getName(), "Name");
        isEmpty(patron.getPhone().toString(), "Phone");

        return patronRepository.save(patron);
    }

    @Transactional
    public void updatePatron(Long id, String name, String email, Integer phone) {
        Patron patron = patronRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Patron with id: " + id + " does not exist"
                ));

        if (name != null &&
                !name.isEmpty() &&
                !Objects.equals(patron.getName(), name)) {
            patron.setName(name);
        }

        if (email != null &&
                !email.isEmpty() &&
                ! Objects.equals(patron.getEmail(), email)) {
            uniqueEmail(email);
            patron.setEmail(email);
        }

        if (phone != null &&
                !Objects.equals(patron.getPhone(), phone)) {
            patron.setPhone(phone);
        }
    }

    public void deletePatron(Long id) {
        patronRepository.deleteById(id);
    }

    // UTIL FUNCTIONS
    public void uniqueEmail(String email) {
        Optional<Patron> patronOptional = patronRepository
                .findPatronByEmail(email);
        if (patronOptional.isPresent()) {
            throw new IllegalStateException("Email taken");
        }
    }

    public void isEmpty(String field, String name) {
        if(field.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, name + " is required");
        }
    }
}