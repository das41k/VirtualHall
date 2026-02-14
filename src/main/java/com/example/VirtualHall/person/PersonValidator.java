package com.example.VirtualHall.person;

import com.example.VirtualHall.utils.PhoneUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class PersonValidator implements Validator {

    private final PersonRepository personRepository;

    // Регулярное выражение для проверки формата телефона
    private static final String PHONE_REGEX = "^(\\+7|8)[\\s-]?\\(?\\d{3}\\)?[\\s-]?\\d{3}[\\s-]?\\d{2}[\\s-]?\\d{2}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    @Autowired
    public PersonValidator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        // 1. Проверка формата телефона
        String rawPhone = person.getPhone();
        if (rawPhone == null || rawPhone.trim().isEmpty()) {
            errors.rejectValue("phone", "", "Телефон не может быть пустым");
        } else if (!PHONE_PATTERN.matcher(rawPhone).matches()) {
            errors.rejectValue("phone", "",
                    "Введите корректный российский номер телефона (например: +7(123)456-78-90, 8(123)4567890, +7 123 456 78 90)");
        } else {
            // 2. Нормализуем телефон для поиска в БД (только если формат правильный)
            String normalizedPhone = PhoneUtil.normalizePhone(rawPhone);

            // 3. Проверка существования пользователя по email
            Optional<Person> existingByEmail = personRepository.findByEmail(person.getEmail());
            if (existingByEmail.isPresent()) {
                errors.rejectValue("email", "",
                        "Пользователь с таким email уже зарегистрирован");
            }

            // 4. Проверка существования пользователя по нормализованному телефону
            Optional<Person> existingByPhone = personRepository.findByPhone(normalizedPhone);
            if (existingByPhone.isPresent()) {
                errors.rejectValue("phone", "",
                        "Пользователь с таким телефоном уже зарегистрирован");
            }
        }

        // 5. Проверка пароля (можно добавить если нужно)
        String password = person.getPassword();
        if (password != null && password.length() < 8) {
            errors.rejectValue("password", "", "Пароль должен содержать минимум 8 символов");
        }
    }
}