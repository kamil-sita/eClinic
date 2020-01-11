package pl.io.e_clinic.controller.login.dto;

import pl.io.e_clinic.entity.employee.model.Employee;
import pl.io.e_clinic.entity.employee.model.Role;
import pl.io.e_clinic.entity.user.model.User;

public class UserInfoResource {

    private final Long userId;
    private final String firstName;
    private final String lastName;
    private final String role;
    private final String username;

    public UserInfoResource(User user) {
        this.userId = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.role = Role.PATIENT.name();
    }

    public UserInfoResource(Employee user) {
        this.userId = user.getUser().getUserId();
        this.firstName = user.getUser().getFirstName();
        this.lastName = user.getUser().getLastName();
        this.username = user.getUser().getUsername();
        this.role = user.getRole().name();
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

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }


}
