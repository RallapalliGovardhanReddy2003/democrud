package demosprings.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demosprings.enity.Employee;
import demosprings.exception.ResourceNotFoundException;
import demosprings.repository.UserRepository;

@Service
@Transactional

public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
		
	}
	
	
	public Employee createUser(Employee user) {
		logger.info("creating user: "+user.getFirstname(),user.getLastname());
		try {
			Employee saveUser = userRepository.save(user);
			logger.info("user created successfully with id: " + saveUser.getId());
			return saveUser;
		} catch (Exception ex){
			logger.error("error ocuurred while creating user: " +ex.getMessage());
			throw ex;
		}
	}
	@Override
	public Employee updateUser( Integer id ,Employee user) {
		logger.info("Updating user with id: {}", id);
		Employee existing =userRepository.findById(id).
				orElseThrow(() -> {
					logger.error("user not found with id: " +id);
					return new ResourceNotFoundException(" user not found"+id);
				});
		
		existing.setFirstname(user.getFirstname());
		existing.setLastname(user.getLastname());
		existing.setMonbno(user.getMonbno());
		existing.setEmailid(user.getEmailid());


		Employee updatedUser = userRepository.save(existing);
		logger.info("User updated successfully with id: {}", updatedUser.getId());
		return updatedUser;
	}
	
	public void deleteUser(Integer id) {
		logger.info("Deleting user with id: {}", id);
		Employee existing =userRepository.findById(id).
				orElseThrow(()-> {
					logger.error("User not found with id: {}", id);
					return new ResourceNotFoundException("User not found with id: " + id);

				});
		userRepository.delete(existing);
		logger.info("User deleted successfully with id: {}", id);
	}
	public Employee getUserById(Integer id) {
		logger.info("Fetching user with id: {}", id);
		return userRepository.findById(id).
				orElseThrow(()->{
					logger.error("User not found with id: {}", id);
					return new ResourceNotFoundException("User not found with id: " + id);
				});
	}
	
	public List<Employee> getAllUsers(){
		logger.info("Fetching all active users");
		return userRepository.findAllActiveEmployees();
	}
	public List<Employee> getAllUsersWithDeleted(){
		logger.info("Fetching all users, including soft-deleted ones");
		return userRepository.findAll();
	}

	public void hardDelete(Integer id){
		logger.info("Hard deleting user with id: {}", id);
		try {
			userRepository.deleteById(id);
			logger.info("User hard deleted successfully with id: {}", id);
		} catch (Exception ex) {
			logger.error("Error occurred while hard deleting user with id: {}", id, ex);
			throw ex;
		}
	}

	public void softDeleteUser(Integer id) {
			logger.info("Soft deleting user with id: {}", id);
			Employee employee = userRepository.findById(id)
					.orElseThrow(() -> {
						logger.error("User not found with id: {}", id);
						return new RuntimeException("Employee not found with id: " + id);
					});
			employee.setDeleted(true);
			userRepository.save(employee);
		logger.info("User soft-deleted successfully with id: {}", id);
	}


}

