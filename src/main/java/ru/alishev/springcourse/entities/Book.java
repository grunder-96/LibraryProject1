package ru.alishev.springcourse.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {

	private int id;
		
	@NotEmpty(message = "Поле не может быть пустым")
	@Size(min = 5, max = 100, message = "Допускается длина от 2 до 100 символов")
	private String name;
	
	@NotEmpty(message = "Поле не может быть пустым")
	@Size(min = 5, max = 100, message = "Допускается длина от 2 до 100 символов")
	private String author;
	
	private int yearOfPublication;

	public Book() {

	}

	public Book(int id, String name, String author, int yearOfPublication) {
		this.id = id;
		this.name = name;
		this.author = author;
		this.yearOfPublication = yearOfPublication;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getYearOfPublication() {
		return yearOfPublication;
	}

	public void setYearOfPublication(int yearOfPublication) {
		this.yearOfPublication = yearOfPublication;
	}
}
