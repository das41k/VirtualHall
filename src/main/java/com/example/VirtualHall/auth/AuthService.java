package com.example.VirtualHall.auth;

import com.example.VirtualHall.person.Person;
import com.example.VirtualHall.person.PersonRepository;
import com.example.VirtualHall.utils.PhoneUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person) {
        person.buildFullName();
        String normalizedPhone = PhoneUtil.normalizePhone(person.getPhone());
        person.setPhone(normalizedPhone);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personRepository.save(person);
    }
}
