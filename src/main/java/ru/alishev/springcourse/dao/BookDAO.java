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
public class BookDAO {
	
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public BookDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<Book> showAll() {
		return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
	}
	
	public void save(Book book) {
		jdbcTemplate.update("INSERT INTO Book(name, author, yearOfPublication) VALUES (?, ?, ?)", book.getName(), book.getAuthor(), book.getYearOfPublication());
	}
	
	public Book getBookById(int id) {
		return jdbcTemplate.queryForObject("SELECT * FROM Book WHERE id = ?", new BeanPropertyRowMapper<>(Book.class), id);
	}
	
	public void edit(Book book) {
		jdbcTemplate.update("UPDATE Book SET name = ?, author = ?, yearOfPublication = ? WHERE id = ?", book.getName(), book.getAuthor(), book.getYearOfPublication(),
				book.getId());
	}
	
	public void updateHumanId(Integer humanId, int id) {
		jdbcTemplate.update("UPDATE Book SET human_id = ? WHERE id = ?", humanId, id);
	}
	
	public void delete(Book book) {
		jdbcTemplate.update("DELETE FROM Book WHERE id = ?", book.getId());
	}
	
	public Optional<Human> getBookOwner(int id) {
		return jdbcTemplate.queryForStream("SELECT Human.* FROM Book JOIN Human ON Book.human_id = Human.id WHERE Book.id = ?", new BeanPropertyRowMapper<>(Human.class), id)
				.findAny();
	}
	
	public void setOwner(int bookId, Human human) {
		jdbcTemplate.update("UPDATE Book SET human_id = ? WHERE id = ?", human.getId(), bookId);
	}
	
	public void resetOwner(int bookId) {
		jdbcTemplate.update("UPDATE Book SET human_id = NULL WHERE id = ?", bookId);
	}
}
