package pl.io.e_clinic.entity.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.io.e_clinic.entity.visit.model.Visit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = {"visits"})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    protected Long userId;

    @NotBlank
    @Size(max = 20, message = "first name can be up to 20 characters long")
    @Column(name = "first_name", nullable = false)
    protected String firstName;

    @NotBlank
    @Size(max = 20, message = "last name can be up to 20 characters long")
    @Column(name = "last_name", nullable = false)
    protected String lastName;

    @NotBlank
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "sex_id", nullable = false)
    protected Sex sex;

    @NotBlank
    @Size(max = 15, message = "pesel can be up to 15 characters long")
    @Column(name = "pesel", nullable = false)
    protected String pesel;

    @NotBlank
    @Size(max = 100, message = "address can be up to 100 characters long")
    @Column(name = "address", nullable = false)
    protected String address;

    @NotBlank
    @Size(max = 20, message = "contact number can be up to 20 characters long")
    @Column(name = "contact_number", nullable = false)
    protected String contactNumber;

    @Size(max = 300, message = "other info can be up to 300 characters long")
    @Column(name = "other_info", nullable = true)
    protected String otherInfo;

    @NotBlank
    @Size(max = 30, message = "username can be up to 30 characters long")
    @Column(name = "username", nullable = false)
    protected String username;

    @NotBlank
    @Size(max = 30, message = "address can be up to 30 characters long")
    @Column(name = "password", nullable = false)
    protected String password;

    @OneToMany(mappedBy = "patient")
    protected Set<Visit> visits;


    public boolean passwordEqual(String password) {
        return this.password.equals(password);
    }

    //gettery nie eksportowane

    public Set<Visit> getVisits() {
        return visits;
    }


    //poniżej nudne gettery i settery


    public String getPassword() {
        return password;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Sex getSex() {
        return sex;
    }

    public String getPesel() {
        return pesel;
    }

    public String getAddress() {
        return address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public String getUsername() {
        return username;
    }

    public User setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public User setSex(Sex sex) {
        this.sex = sex;
        return this;
    }

    public User setPesel(String pesel) {
        this.pesel = pesel;
        return this;
    }

    public User setAddress(String address) {
        this.address = address;
        return this;
    }

    public User setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public User setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
        return this;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
}
