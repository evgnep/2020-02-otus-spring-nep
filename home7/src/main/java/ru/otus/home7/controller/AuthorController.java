package ru.otus.home7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.home7.domain.Author;
import ru.otus.home7.repository.AuthorRepository;

@Controller
public class AuthorController {
    private final AuthorRepository repository;

    public AuthorController(AuthorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/authors")
    public String list(Model model) {
        var authors = repository.findAll();
        model.addAttribute("authors", authors);
        return "authors";
    }

    @GetMapping("/author/delete")
    public ModelAndView delete(@RequestParam("id") long id) {
        repository.deleteById(id);
        return new ModelAndView("redirect:/authors");
    }

    @GetMapping("/author/create")
    public String create(Model model) {
        model.addAttribute("author", Author.builder().id(0).build());
        return "author_detail";
    }

    @GetMapping("/author/edit")
    public String edit(@RequestParam("id") long id, Model model) {
        model.addAttribute("author", repository.findById(id).orElseThrow());
        return "author_detail";
    }

    @PostMapping("/author/save")
    public ModelAndView save(Author a) {
        repository.save(a);
        return new ModelAndView("redirect:/authors");
    }
}
