package site.benitohuerta.starter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import site.benitohuerta.starter.entity.Role;
import site.benitohuerta.starter.entity.User;
import site.benitohuerta.starter.service.RoleService;
import site.benitohuerta.starter.service.UserService;
import site.benitohuerta.starter.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("users")
public class UserController {

    @GetMapping("/")
    public String home()
    {
        return "admin/users/index";
    }

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("")
    public String index(Model model, HttpServletRequest request)
    {
        List<User> users = userService.findAll();

        model.addAttribute("users", users);
        model.addAttribute("message", request.getParameter("message"));

        return "admin/users/index";
    }

    @GetMapping("create")
    public String create(Model model)
    {
        User user = new User();
        List<Role> roles = roleService.findAll();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);

        return "admin/users/create";
    }

    @PostMapping("")
    public String store(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes, BindingResult bindingResult)
    {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            List<Role> roles = roleService.findAll();
            model.addAttribute("roles", roles);

            return "admin/users/create";
        }

        List<Integer> ids = new ArrayList<>();

        for (Role role: user.getRoles()) {
            ids.add(role.getId());
        }

        user.setRoles(new HashSet< >(roleService.getRoleByIds(ids)));
        user.setPassword("password");

        userService.save(user);
        redirectAttributes.addAttribute("message", "New user added!");

        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable(value = "id") Integer id, Model model)
    {
        User user = userService.findById(id);
        List<Role> roles = roleService.findAll();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);

        return "admin/users/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(value = "id") Integer id, Model model)
    {
        User user = userService.findById(id);
        List<Role> roles = roleService.findAll();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);

        return "admin/users/edit";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable(value = "id") Integer id, @ModelAttribute User userDetails,
                         RedirectAttributes redirectAttributes, BindingResult bindingResult)
    {
        userValidator.validate(userDetails, bindingResult);

        if (bindingResult.hasErrors()) {
            return "admin/users/edit";
        }

        List<Integer> ids = new ArrayList<>();

        for (Role role: userDetails.getRoles()) {
            ids.add(role.getId());
        }

        System.out.println(roleService.getRoleByIds(ids));
        userDetails.setRoles(new HashSet< >(roleService.getRoleByIds(ids)));

        userService.update(id, userDetails);
        redirectAttributes.addAttribute("message", "The user has been updated!");

        return "redirect:/users";
    }
}
