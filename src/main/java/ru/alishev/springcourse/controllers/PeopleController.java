package ru.alishev.springcourse.controllers;

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
import ru.alishev.springcourse.entities.Human;
import ru.alishev.springcourse.util.HumanValidator;

@Controller
@RequestMapping("/people")
public class PeopleController {
	
	private final HumanDAO humanDAO;
	private final HumanValidator humanValidator;
	
	public PeopleController(HumanDAO humanDAO, HumanValidator humanValidator) {
		this.humanDAO = humanDAO;
		this.humanValidator = humanValidator;
	}
	
	@GetMapping()
	public String showAll(Model model) {
		model.addAttribute("people", humanDAO.showAll());
		return "people/showAll";
	}
	
	@GetMapping("/new")
	public String showFormNewHuman(@ModelAttribute("newHuman") Human human) {
		return "people/new";
	}
	
	@PostMapping()
	public String createNewHuman(@ModelAttribute("newHuman") @Valid Human human, BindingResult bindingResult) {
		humanValidator.validate(human, bindingResult);
		if (bindingResult.hasErrors()) {
			return "/people/new";
		}
		humanDAO.save(human);
		return "redirect:/people";
	}
	
	@GetMapping("/{id}")
	public String showHuman(@PathVariable("id") int id, Model model) {
		model.addAttribute("human", humanDAO.getHumanById(id));
		model.addAttribute("books", humanDAO.showAllBooksByHumanId(id));
		return "people/showHuman";
	}
	
	@GetMapping("/{id}/edit")
	public String showEditHumanForm(@PathVariable("id") int id, Model model) {
		model.addAttribute("human", humanDAO.getHumanById(id));
		return "people/edit";
	}
	
	@PatchMapping("/{id}")
	public String saveModifiedHuman(@ModelAttribute("human") @Valid Human human, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "people/edit";
		}
		humanDAO.edit(human);
		return "redirect:/people";
	}
	
	@DeleteMapping("/{id}")
	public String deleteHuman(@PathVariable("id") int id) {
		Human human = humanDAO.getHumanById(id);
		humanDAO.delete(human);
		return "redirect:/people";
	}
}
