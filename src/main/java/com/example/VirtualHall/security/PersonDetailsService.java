package com.example.VirtualHall.security;

import com.example.VirtualHall.person.Person;
import com.example.VirtualHall.person.PersonRepository;
import com.example.VirtualHall.utils.PhoneUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        System.out.println("Поиск пользователя: " + login);

        Optional<Person> byEmail = personRepository.findByEmail(login);
        if (byEmail.isPresent()) {
            return new PersonDetails(byEmail.get());
        }

        String normalizedPhone = PhoneUtil.normalizePhone(login);

        Person person = personRepository.findByPhone(normalizedPhone)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Пользователь не найден: " + login
                ));

        return new PersonDetails(person);
    }
}
