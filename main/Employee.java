package main;

import java.util.Date;

import annotations.CD_Class;
import annotations.CD_Parameter;

@CD_Class
public class Employee {
	@CD_Parameter
	private String name, surname;
	@CD_Parameter
	private Date birthDate;
}
