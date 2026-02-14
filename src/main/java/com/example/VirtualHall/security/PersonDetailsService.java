package com.example.VirtualHall.security;

import com.example.VirtualHall.person.Person;
import com.example.VirtualHall.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Person person = personRepository.findByPhoneOrEmail(login)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Пользователь с данным телефоном/почтой: " + login + " не был найден!"
                ));
        return new PersonDetails(person);
    }
}
