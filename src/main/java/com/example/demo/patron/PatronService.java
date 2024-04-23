package com.example.demo.patron;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void addNewPatron(Patron patron) {
        patronRepository.save(patron);
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
            Optional<Patron> studentOptional = patronRepository
                    .findPatronByEmail(email);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("email taken");
            }
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
}
