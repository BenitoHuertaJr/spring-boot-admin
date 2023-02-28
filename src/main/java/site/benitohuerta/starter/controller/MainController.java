package site.benitohuerta.starter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import site.benitohuerta.starter.entity.User;
import site.benitohuerta.starter.service.UserService;
import site.benitohuerta.starter.validator.UserRegistrationValidator;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRegistrationValidator userRegistrationValidator;

    @GetMapping({"/", "/welcome"})
    public String index()
    {
        return "index";
    }

    @GetMapping("/signup")
    public String registration(Model model) {
        model.addAttribute("user", new User());

        return "signup";
    }

    @PostMapping("/signup")
    public String registration(@ModelAttribute("user") User user, BindingResult bindingResult) {
        userRegistrationValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "signup";
        }

        userService.registration(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
}
