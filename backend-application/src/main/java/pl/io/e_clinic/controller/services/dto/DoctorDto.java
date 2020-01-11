package pl.io.e_clinic.controller.services.dto;

public class DoctorDto {
    //user.user_id, user.first_name, user.last_name
    private Long userId;
    private String firstName;
    private String lastName;

    public Long getUserId() {
        return userId;
    }

    public DoctorDto setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public DoctorDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public DoctorDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}