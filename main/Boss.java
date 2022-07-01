package main;

import java.util.List;

import annotations.CD_Class;
import annotations.CD_OneToMany;
import annotations.CD_Parameter;

@CD_Class
public class Boss {
	@CD_OneToMany(many = main.Employee.class, connectionName = "answersTo")
	private List<Employee> persons;
	@CD_Parameter
	private String name, surname;
	@CD_Parameter
	public double salary;
}
