package ru.alishev.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import ru.alishev.springcourse.dao.BookDAO;
import ru.alishev.springcourse.dao.HumanDAO;
import ru.alishev.springcourse.entities.Book;
import ru.alishev.springcourse.entities.Human;

@Controller
@RequestMapping("/books")
public class BooksController {
	
	private final BookDAO bookDAO;
	private final HumanDAO humanDAO;
	
	@Autowired
	public BooksController(BookDAO bookDAO, HumanDAO humanDAO) {
		this.bookDAO = bookDAO;
		this.humanDAO = humanDAO;
	}
	
	@GetMapping()
	public String showAll(Model model) {
		model.addAttribute("books", bookDAO.showAll());
		return "books/showAll";
	}
	
	@GetMapping("/new")
	public String showFormNewBook(@ModelAttribute("newBook") Book book) {
		return "books/new";
	}
	
	@PostMapping()
	public String createNewBook(@ModelAttribute("newBook") @Valid Book book, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "books/new";
		}
		bookDAO.save(book);
		return "redirect:/books";
	}
	
	@GetMapping("/{id}")
	public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("human") Human human) {
		if (bookDAO.getBookOwner(id).isPresent()) {
			model.addAttribute("owner", bookDAO.getBookOwner(id).get());
		} else {
			model.addAttribute("people", humanDAO.showAll());
		}
		model.addAttribute("book", bookDAO.getBookById(id));
		return "books/showBook";
	}
	
	@GetMapping("/{id}/edit")
	public String showEditBookForm(@PathVariable("id") int id, Model model) {
		model.addAttribute("book", bookDAO.getBookById(id));
		return "books/edit";
	}
	
	@PatchMapping("/{id}")
	public String saveModifiedBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "books/edit";
		}
		bookDAO.edit(book);
		return "redirect:/books";
	}
	
	@DeleteMapping("/{id}")
	public String deleteBook(@PathVariable("id") int id) {
		Book book = bookDAO.getBookById(id);
		bookDAO.delete(book);
		return "redirect:/books";
	}
	
	@PatchMapping("/{id}/set_owner")
	public String setOwner(@PathVariable("id") int bookId, @ModelAttribute("human") Human human) {
		bookDAO.setOwner(bookId, human);
		return "redirect:/books/" + bookId;
	}
	
	@PatchMapping("/{id}/reset_owner")
	public String resetOwner(@PathVariable("id") int bookId) {
		bookDAO.resetOwner(bookId);
		return "redirect:/books/" + bookId;
	}
}
