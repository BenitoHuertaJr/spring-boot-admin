package site.benitohuerta.starter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import site.benitohuerta.starter.entity.Entry;
import site.benitohuerta.starter.entity.Role;
import site.benitohuerta.starter.entity.User;
import site.benitohuerta.starter.service.EntryService;
import site.benitohuerta.starter.service.FilesStorageService;
import site.benitohuerta.starter.service.RoleService;
import site.benitohuerta.starter.service.UserService;
import site.benitohuerta.starter.validator.EntryValidator;
import site.benitohuerta.starter.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("entries")
public class EntryController {

    @Autowired
    private EntryService entryService;

    @Autowired
    private EntryValidator entryValidator;

    @Autowired
    FilesStorageService storageService;

    @GetMapping("")
    public String index(Model model, HttpServletRequest request)
    {
        List<Entry> entries = entryService.findAll();

        model.addAttribute("entries", entries);
        model.addAttribute("message", request.getParameter("message"));

        return "admin/entries/index";
    }

    @GetMapping("create")
    public String create(Model model)
    {
        Entry entry = new Entry();

        model.addAttribute("entry", entry);

        return "admin/entries/create";
    }

    @PostMapping("")
    public String store(@ModelAttribute Entry entry, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, BindingResult bindingResult)
    {
        entryValidator.validate(entry, bindingResult);

        if (bindingResult.hasErrors()) {
            return "admin/entries/create";
        }

        try {
            storageService.save(file);
            entry.setImage(file.getOriginalFilename());
            System.out.println(entry.getImage());

        } catch (Exception e) {
            String error = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            System.out.println(error);
            redirectAttributes.addAttribute("error", error);
            return "admin/entries/create";
        }

        entryService.save(entry);
        redirectAttributes.addAttribute("message", "New entry added!");

        return "redirect:/entries";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable(value = "id") Integer id, Model model)
    {
        Entry entry = entryService.findById(id);
        model.addAttribute("entry", entry);

        return "admin/entries/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(value = "id") Integer id, Model model)
    {
        Entry entry = entryService.findById(id);
        model.addAttribute("entry", entry);

        return "admin/entries/edit";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable(value = "id") Integer id, @ModelAttribute Entry entryDetails,
                         @RequestParam MultipartFile image, RedirectAttributes redirectAttributes,
                         BindingResult bindingResult)
    {
        entryValidator.validate(entryDetails, bindingResult);

        if (bindingResult.hasErrors()) {
            return "admin/entries/edit";
        }

        try {
            storageService.save(image);
            entryDetails.setImage(image.getOriginalFilename());

        } catch (Exception e) {
            String error = "Could not upload the file: " + image.getOriginalFilename() + ". Error: " + e.getMessage();
            redirectAttributes.addAttribute("error", error);
            return "admin/entries/edit";
        }

        entryService.update(id, entryDetails);
        redirectAttributes.addAttribute("message", "The entry has been updated!");

        return "redirect:/entries";
    }
}
