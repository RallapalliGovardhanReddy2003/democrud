package demosprings.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demosprings.enity.Employee;
import demosprings.exception.ResourceNotFoundException;
import demosprings.repository.UserRepository;

@Service
@Transactional

public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
		
	}
	
	
	public Employee createUser(Employee user) {
		return userRepository.save(user);
	}
	@Override
	public Employee updateUser( Integer id ,Employee user) {
		Employee existing =userRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException(" user not found"+id));
		
		existing.setFirstname(user.getFirstname());
		existing.setLastname(user.getLastname());
		existing.setMonbno(user.getMonbno());
		existing.setEmailid(user.getEmailid());
		
		return userRepository.save(existing);
	}
	
	public void deleteUser(Integer id) {
		
		Employee existing =userRepository.findById(id).
				orElseThrow(()-> new ResourceNotFoundException(" user not found"+id));
		userRepository.delete(existing);
	}
	public Employee getUserById(Integer id) {
		return userRepository.findById(id).
				orElseThrow(()-> new ResourceNotFoundException(" user not found"+id));
	}
	
	public List<Employee> getAllUsers(){
		return userRepository.findAllActiveEmployees();
	}

	public void hardDelete(Integer id){
		 userRepository.deleteById(id);
	}

	public void softDeleteUser(Integer id) {
		Employee employee =userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
		employee.setDeleted(true);
		userRepository.save(employee);
	}

}

