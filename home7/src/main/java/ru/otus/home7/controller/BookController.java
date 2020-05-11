package ru.otus.home7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.home7.domain.Book;
import ru.otus.home7.domain.BookComment;
import ru.otus.home7.repository.AuthorRepository;
import ru.otus.home7.repository.BookRepository;
import ru.otus.home7.repository.GenreRepository;

@Controller
public class BookController {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @GetMapping("/books")
    public String books(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "books";
    }

    @GetMapping("/book/delete")
    public ModelAndView delete(@RequestParam("id") long id) {
        bookRepository.deleteById(id);
        return new ModelAndView("redirect:/books");
    }

    @GetMapping("/book/info")
    @Transactional(readOnly = true)
    public String info(@RequestParam("id") long id, Model model) {
        var book = bookRepository.findById(id).orElseThrow();
        book.getComments().size();// load comments

        model.addAttribute("book", book);
        return "book_info";
    }

    @PostMapping("/book/add_comment")
    @Transactional
    public ModelAndView addComment(@RequestParam("bookId") long bookId, BookComment comment, Model model) {
        comment.setId(0);
        var book = bookRepository.findById(bookId).orElseThrow();
        book.getComments().add(comment);

        model.addAttribute("id", bookId);
        return new ModelAndView("redirect:/book/info", model.asMap());
    }

    @GetMapping("/book/edit")
    @Transactional(readOnly = true)
    public String edit(@RequestParam("id") long id, Model model) {
        model.addAttribute("book", bookRepository.findById(id).orElseThrow());
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return "book_detail";
    }

    @GetMapping("/book/create")
    @Transactional(readOnly = true)
    public String create(Model model) {
        model.addAttribute("book", Book.builder().id(0).build());
        model.addAttribute("authors", authorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        return "book_detail";
    }

    @PostMapping("/book/save")
    @Transactional
    public ModelAndView save(Book book, Model model) {
        bookRepository.save(book);
        return new ModelAndView("redirect:/books");
    }
}
