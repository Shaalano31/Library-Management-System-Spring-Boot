package com.example.demo.repository;

import com.example.demo.model.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatronRepository  extends JpaRepository<Patron, Long> {

    @Query("SELECT p FROM Patron p WHERE p.email = ?1")
    Optional<Patron> findPatronByEmail(String email);
}
