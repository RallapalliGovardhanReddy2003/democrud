package demosprings.controller;
import java.util.List;


import demosprings.service.EmailService;
import demosprings.service.SmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import demosprings.enity.Employee;
import demosprings.service.UserService;

@RestController
@RequestMapping("/User")
@Validated
public class UserController {


	private final UserService userService;
    private final EmailService emailService;
    private final SmsService smsService;
    @Value("${twilio.from-number}")
    private String from;

    public UserController(UserService userService, EmailService emailService, SmsService smsService) {
        this.userService = userService;
        this.emailService = emailService;
        this.smsService = smsService;
    }

    @PostMapping("/createuser")
    public ResponseEntity<Employee> createUser(@Validated @RequestBody Employee user,
                                               @RequestParam(defaultValue = "false") boolean sendEmail,
                                               @RequestParam(defaultValue = "false") boolean sendSms) {
        Employee created = userService.createUser(user);
        if(sendEmail){
        emailService.sendSimple(created.getEmailid(), "registration confirmataion",
                "successfully registered");}
        if(sendSms){
        String sid = smsService.sendSms("+17754025856", created.getMonbno(), "registered employee");
        }
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
	@GetMapping("/getallusers")
    public ResponseEntity<List<Employee>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


	@GetMapping("/getuser/{id}")
    public ResponseEntity<Employee> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

	 @PutMapping("/updateuser/{id}")
	    public ResponseEntity<Employee> updateUser(@PathVariable Integer id,
	                                           @Validated @RequestBody Employee user) {
	        return ResponseEntity.ok(userService.updateUser(id, user));
	    }
    @DeleteMapping("/harddelete/{id}")
    public ResponseEntity<String> harddelete(@PathVariable Integer id){
        userService.deleteUser(id);
       return ResponseEntity.ok("deleted successfully");
    }
    @DeleteMapping ("/softdelete/{id}")
    public ResponseEntity<String> softDeleteUser(@PathVariable Integer id){
        userService.softDeleteUser(id);
       return ResponseEntity.ok("deleted successfully");

    }






}

