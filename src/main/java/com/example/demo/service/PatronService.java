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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PatronService {

    private static final Logger logger = LoggerFactory.getLogger(PatronService.class);
    private final PatronRepository patronRepository;

    @Autowired
    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public List<Patron> getPatrons() {
        logger.info("Fetching all patrons");
        return patronRepository.findAll();
    }

    public Optional<Patron> getPatronById(Long id) {
        logger.info("Fetching patron from db with id {}", id);
        return patronRepository.findById(id);
    }

    public Patron addNewPatron(@RequestBody Patron patron) {

        isEmpty(patron.getEmail(), "Email");
        uniqueEmail(patron.getEmail());
        isEmpty(patron.getName(), "Name");
        isEmpty(patron.getPhone().toString(), "Phone");
        logger.info("Patron Added");
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
        logger.info("Patron with id {} updated", id);
    }

    public void deletePatron(Long id) {
        patronRepository.deleteById(id);
        logger.info("Patron with id {} deleted", id);
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
