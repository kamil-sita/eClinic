package pl.io.e_clinic.controller.login;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.io.e_clinic.controller.login.dto.UserInfoResource;
import pl.io.e_clinic.entity.employee.model.Employee;
import pl.io.e_clinic.entity.employee.repository.EmployeeRepository;
import pl.io.e_clinic.entity.user.model.User;
import pl.io.e_clinic.entity.user.model.UserPrincipal;
import pl.io.e_clinic.entity.user.repository.impl.UserRepositoryImpl;

import java.util.Optional;
import java.util.stream.StreamSupport;


@RestController
@RequestMapping("/auth")
public class LoginController {
    private static final Logger log = LogManager.getLogger(LoginController.class);

    @Autowired
    UserRepositoryImpl userRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UserInfoResource> getUserInfo() {

        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        validatePrinciple(authenticationToken.getPrincipal());
        UserPrincipal userPrincipal = (UserPrincipal) authenticationToken.getPrincipal();
        User authenticatedUser = userRepository.getUserByUsername(userPrincipal.getUsername());

        if (authenticatedUser == null)
            throw new AuthenticationException("User not authenticated") {
            };


        //check whether the user is an employee
        Optional<Employee> authenticatedEmployee = StreamSupport
                .stream(employeeRepository.findAll().spliterator(), false)
                .filter(employee -> authenticatedUser.getUserId().longValue() == employee.getUser().getUserId().longValue())
                .findAny();

        UserInfoResource userInfoResource;

        userInfoResource = authenticatedEmployee
                .map(UserInfoResource::new)
                .orElseGet(() -> new UserInfoResource(authenticatedUser));

        return ResponseEntity.ok(userInfoResource);
    }

    private void validatePrinciple(Object principal) {
        if (!(principal instanceof UserPrincipal)) {
            throw new IllegalArgumentException("Principal can not be null!");
        }
    }
}
