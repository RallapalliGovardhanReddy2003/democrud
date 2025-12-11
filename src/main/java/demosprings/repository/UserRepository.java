package demosprings.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import demosprings.enity.Employee;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository  extends JpaRepository<Employee,Integer>{

    @Query("SELECT e FROM Employee e WHERE e.isDeleted = false")
    List<Employee> findAllActiveEmployees();

}

