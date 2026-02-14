package com.example.VirtualHall.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByEmail(String email);
    Optional<Person> findByPhone(String phone);
    Optional<Person> findByPhoneOrEmail(String phone, String email);
}
