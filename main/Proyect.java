package main;

import java.util.List;

import annotations.CD_Class;
import annotations.CD_OneToMany;
import annotations.CD_Parameter;

@CD_Class
public class Proyect {
	@CD_Parameter
	public Boss manager;
	@CD_OneToMany(many = main.Employee.class, connectionName = "worksIn")
	private List<Employee> members;
	@CD_OneToMany(many = main.Code.class, connectionName = "partOf")
	private List<Code> codes;
}
