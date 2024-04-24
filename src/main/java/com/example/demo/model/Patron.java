package com.example.demo.model;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table
public class Patron {

    @Id
    @SequenceGenerator(
            name = "patron_sequence",
            sequenceName = "patron_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "patron_sequence"
    )
    private Long id;
    private String name;
    private Integer phone;
    private String email;

    @OneToMany(mappedBy = "patron")
    Set<Borrowing> borrowing;

    public Patron() {
    }

    public Patron(Long id, String name, Integer phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Patron(String name, Integer phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Patron{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
