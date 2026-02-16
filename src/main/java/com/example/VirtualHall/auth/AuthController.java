package com.example.VirtualHall.auth;

import com.example.VirtualHall.person.Person;
import com.example.VirtualHall.person.PersonValidator;
import com.example.VirtualHall.utils.PhoneUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final PersonValidator personValidator;
    private final AuthService authService;

    @Autowired
    public AuthController(PersonValidator personValidator, AuthService authService) {
        this.personValidator = personValidator;
        this.authService = authService;
    }

    @GetMapping
    public String getMainPage() {
        return "redirect:/events";
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        model.addAttribute("person", new Person());
        return "auth/register";
    }

    @PostMapping("/process-register")
    public String register(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        authService.register(person);
        return "redirect:/login?registered";
    }
}
