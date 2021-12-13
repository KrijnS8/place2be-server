package nl.krijnschelvis.place2beserver;

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
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/register-user")
    public @ResponseBody String registerUser(@RequestParam String firstName
            , @RequestParam String lastName, @RequestParam String email, @RequestParam String password) {

        // Create user bean
        User n = new User();
        n.setFirstName(firstName);
        n.setLastName(lastName);
        n.setEmail(email);
        n.setPassword(password);

        // Try to add user to database
        try {
            userRepository.save(n);
        } catch (Exception e) {
            return "Email already in use";
        }
        return "Saved";
    }

    @GetMapping(path="/get-user-data")
    public @ResponseBody User signInUser(@RequestParam String email, @RequestParam String password) {
        // Validate credentials
        if (!userRepository.existsByEmailAndPassword(email, password)) {
            return new User();
        }

        // Return user object
        User user = userRepository.findUserByEmail(email);
        user.setPassword(null);
        return user;
    }
}
