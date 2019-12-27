package pl.io.e_clinic.entity.user.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    public Long userId;

    @NotBlank
    @Size(max = 20, message = "first name can be up to 20 characters long")
    @Column(name = "first_name", nullable = false)
    public String firstName;

    @NotBlank
    @Size(max = 20, message = "last name can be up to 20 characters long")
    @Column(name = "last_name", nullable = false)
    public String lastName;

    @NotBlank
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "sex_id", nullable = false)
    public Sex sex;

    @NotBlank
    @Size(max = 15, message = "pesel can be up to 15 characters long")
    @Column(name = "pesel", nullable = false)
    public String pesel;

    @NotBlank
    @Size(max = 100, message = "address can be up to 100 characters long")
    @Column(name = "address", nullable = false)
    public String address;

    @NotBlank
    @Size(max = 20, message = "contact number can be up to 20 characters long")
    @Column(name = "contact_number", nullable = false)
    public String contactNumber;

    @Size(max = 300, message = "other info can be up to 300 characters long")
    @Column(name = "other_info", nullable = true)
    public String otherInfo;

    @NotBlank
    @Size(max = 30, message = "username can be up to 30 characters long")
    @Column(name = "username", nullable = false)
    public String username;

    @NotBlank
    @Size(max = 30, message = "address can be up to 30 characters long")
    @Column(name = "password", nullable = false)
    private String password; //super bezpieczenstwo w 2019/2020 - haslo w plaintexcie, ale jest PRIVATE !!!!!

}
