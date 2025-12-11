package demosprings.enity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;


@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    
    private String firstname;

   
    private String lastname;

   // @Pattern(regexp = "^[98][0-9]{9}$", message = "Mobile number must start with 9 or 8 and be 10 digits")
    private String monbno;   // changed to String for pattern validation

    @Pattern(regexp = "^[A-Za-z0-9.]+@gmail\\.com$", message = "Email must be valid and end with @gmail.com")
    private String emailid;

    private boolean isDeleted;

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
// getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMonbno() {
        return monbno;
    }

    public void setMonbno(String monbno) {
        this.monbno = monbno;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }
}
