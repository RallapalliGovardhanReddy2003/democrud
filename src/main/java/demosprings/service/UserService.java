package demosprings.service;


import java.util.List;

import demosprings.enity.Employee;

public interface UserService {
	
	Employee createUser(Employee user);
	
	Employee updateUser(Integer id, Employee user);
	
	void deleteUser(Integer id);
	
	Employee getUserById(Integer id);
	
	List<Employee> getAllUsers();


	void softDeleteUser(Integer id);
}


