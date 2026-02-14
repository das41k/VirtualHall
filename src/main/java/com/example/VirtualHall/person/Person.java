package com.example.VirtualHall.person;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "person")
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "person_id")
    private Long personId;

    @Column(name="fio", nullable = false)
    private String personFio;

    @Transient
    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(min = 2, max = 50, message = "Фамилия должна содержать от 2 до 50 символов")
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z\\-]+$",
            message = "Фамилия может содержать только буквы и дефис")
    private String lastName;

    @Transient
    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно содержать от 2 до 50 символов")
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+$",
            message = "Имя может содержать только буквы")
    private String firstName;

    @Transient
    @Size(max = 50, message = "Отчество должно содержать до 50 символов")
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]*$",
            message = "Отчество может содержать только буквы")
    private String middleName;

    @Column(name = "phone", nullable = false, unique = true)
    @NotBlank(message = "Телефон не может быть пустым")
    @Pattern(
            regexp = "^(\\+7|8)[\\s-]?\\(?\\d{3}\\)?[\\s-]?\\d{3}[\\s-]?\\d{2}[\\s-]?\\d{2}$",
            message = "Введите корректный российский номер телефона (например: +7(123)456-78-90, 8(123)4567890, +7 123 456 78 90)"
    )
    private String phone;

    @Column(name="email", nullable = false, unique = true)
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный формат email")
    private String email;

    @Column(name="password", nullable = false)
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 8, message = "Пароль должен содержать минимум 8 символов")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "Пароль должен содержать минимум 8 символов, включая: 1 цифру, 1 заглавную букву, 1 строчную букву и 1 спецсимвол (@#$%^&+=!)")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role = Role.USER;

    public void buildFullName() {
        this.personFio = (lastName + " " + firstName + " " + (middleName != null ? middleName : "")).trim();
    }
}
