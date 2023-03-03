package site.benitohuerta.starter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import site.benitohuerta.starter.entity.Entry;
import site.benitohuerta.starter.entity.User;
import site.benitohuerta.starter.service.EntryService;
import site.benitohuerta.starter.service.UserService;
import site.benitohuerta.starter.validator.UserRegistrationValidator;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private EntryService entryService;

    @Autowired
    private UserRegistrationValidator userRegistrationValidator;

    @GetMapping({"/", "/welcome"})
    public String index(Model model)
    {
        List<Entry> entries = entryService.findAll();
        model.addAttribute("entries", entries);

        return "index";
    }

    @GetMapping("blog/{slug}")
    public String show(@PathVariable(value = "slug") String slug, Model model)
    {
        Entry entry = entryService.getEntryBySlug(slug);
        model.addAttribute("entry", entry);

        return "blog/show";
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
