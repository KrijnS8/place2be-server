package nl.krijnschelvis.place2beserver;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path="/authentication")
public class AuthenticationController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/register-user")
    public @ResponseBody User registerUser(@RequestParam String firstName
            , @RequestParam String lastName, @RequestParam String email, @RequestParam String password) {

        // Checks if email already exists
        if (userRepository.existsByEmail(email)) {
            // Return empty user bean
            return new User();
        }

        // Create user bean
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);

        // Try to add user to database
        try {
            userRepository.save(user);
        } catch (Exception e) {
            // Return empty user bean
            return new User();
        }

        // Return user
        return user;
    }

    @GetMapping(path="/get-user-data")
    public @ResponseBody User signInUser(@RequestParam String email, @RequestParam String password) {
        // Validate credentials
        if (!userRepository.existsByEmailAndPassword(email, password)) {
            return new User();
        }

        // Return user
        User user = userRepository.findUserByEmail(email);
        user.setPassword(null);
        return user;
    }
}
