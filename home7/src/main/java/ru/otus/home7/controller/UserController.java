package ru.otus.home7.controller;


import com.google.common.base.Strings;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.home7.domain.User;
import ru.otus.home7.repository.UserRepository;

@Controller
public class UserController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public String list(Model model) {
        var users = repository.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/user/delete")
    public ModelAndView delete(@RequestParam("id") long id) {
        repository.deleteById(id);
        return new ModelAndView("redirect:/users");
    }

    @GetMapping("/user/create")
    public String create(Model model) {
        model.addAttribute("user", User.builder().id(0).build());
        return "user_detail";
    }

    @GetMapping("/user/edit")
    public String edit(@RequestParam("id") long id, Model model) {
        model.addAttribute("user", repository.findById(id).orElseThrow());
        return "user_detail";
    }

    @PostMapping("/user/save")
    @Transactional
    public ModelAndView save(User u) {
        if (u.getId() != 0 && Strings.isNullOrEmpty(u.getPassword())) {
            var cur = repository.findById(u.getId()).orElseThrow();
            u.setPassword(cur.getPassword());
        } else
            u.setPassword(passwordEncoder.encode(u.getPassword()));

        repository.save(u);
        return new ModelAndView("redirect:/users");
    }
}
