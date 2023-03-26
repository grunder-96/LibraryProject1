package ru.alishev.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.alishev.springcourse.dao.HumanDAO;
import ru.alishev.springcourse.entities.Human;

@Component
public class HumanValidator implements Validator{
	
	private final HumanDAO humanDAO;
	
	@Autowired
	public HumanValidator(HumanDAO humanDAO) {
		this.humanDAO = humanDAO;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Human.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Human human = (Human) target;
		if (humanDAO.getHumanByFullName(human.getFull_name()).isPresent()) {
			errors.rejectValue("full_name", "", "Такой человек уже есть");
		}
	}
}

//@Override
//public void validate(Object target, Errors errors) {
//	Person person = (Person) target;
//	if (personDAO.show(person.getEmail()).isPresent()) {
//		errors.rejectValue("email", "", "Этот email уже успользуется");
//	}