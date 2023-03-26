package ru.alishev.springcourse.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.alishev.springcourse.entities.Book;
import ru.alishev.springcourse.entities.Human;

@Component
public class HumanDAO {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public HumanDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Human> showAll() {
		return jdbcTemplate.query("SELECT * FROM Human", new BeanPropertyRowMapper<>(Human.class));
	}
		
	public Human getHumanById(int id) {
		return jdbcTemplate.queryForObject("SELECT * FROM Human WHERE id = ?", new BeanPropertyRowMapper<>(Human.class), id);
	}
	
	public Optional<Human> getHumanByFullName(String fullName) {
		return jdbcTemplate.query("SELECT FROM Human WHERE full_name = ?", new Object[] {fullName}, new BeanPropertyRowMapper<>(Human.class))
				.stream().findAny();
	}
	
	public void save(Human human) {
		jdbcTemplate.update("INSERT INTO Human(full_name, year_of_birth) VALUES (?, ?)", human.getFull_name(), human.getYear_of_birth());
	}
	
	public void edit(Human human) {
		jdbcTemplate.update("UPDATE Human SET full_name = ?, year_of_birth = ? WHERE id = ?", human.getFull_name(), human.getYear_of_birth(),
				human.getId());
	}
	
	public void delete(Human human) {
		jdbcTemplate.update("DELETE FROM Human WHERE id = ?", human.getId());
	}
	
	public List<Book> showAllBooksByHumanId(int id) {
		return jdbcTemplate.query("SELECT * FROM Book WHERE human_id = ?", new BeanPropertyRowMapper<>(Book.class), id);
	}
}
