package demosprings.service;



import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import demosprings.enity.Employee;
import demosprings.events.UserCreatedEvent;
import demosprings.repository.UserRepository;

@Service
public class EmployeeManager {

    private final UserRepository userRepository;
    private final ApplicationEventPublisher publisher;

    public EmployeeManager(UserRepository userRepository, ApplicationEventPublisher publisher) {
        this.userRepository = userRepository;
        this.publisher = publisher;
    }

    @Transactional
    public Employee create(Employee e) {
        Employee saved = userRepository.save(e);
        // publish event (listener will react AFTER COMMIT)
        publisher.publishEvent(new UserCreatedEvent(saved));
        return saved;
    }

    @Transactional
    public Employee update(Integer id, Employee payload) {
        Employee existing = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        existing.setFirstname(payload.getFirstname());
        existing.setLastname(payload.getLastname());
        existing.setMonbno(payload.getMonbno());
        existing.setEmailid(payload.getEmailid());
        return userRepository.save(existing);
    }

    @Transactional(readOnly = true)
    public java.util.List<Employee> listAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Employee getById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(Integer id) {
    	userRepository.deleteById(id);
    }


}
