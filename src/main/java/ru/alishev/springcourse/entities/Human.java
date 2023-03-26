package ru.alishev.springcourse.entities;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Human {
	
	private int id;
	
	@NotEmpty(message = "Поле не может быть пустым")
	@Size(min = 5, max = 200, message = "Допускается длина от 5 до 200 символов")
	private String full_name;
	
	@Min(value = 1900, message = "Значение не может быть меньше 1900")
	@Max(value = 2023, message = "Значение не может быть больше 2023")
	private int year_of_birth;
	
	public Human() {
		
	}

	public Human(int id, String full_name, int year_of_birth) {
		super();
		this.id = id;
		this.full_name = full_name;
		this.year_of_birth = year_of_birth;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public int getYear_of_birth() {
		return year_of_birth;
	}

	public void setYear_of_birth(int year_of_birth) {
		this.year_of_birth = year_of_birth;
	}
}
