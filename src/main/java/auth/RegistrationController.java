package auth;


import auth.repo.UserRepository;
import auth.request.RegistrationRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;

@Controller("/register")
@Secured(SecurityRule.IS_ANONYMOUS)
public class RegistrationController {

    @Inject
    private UserRepository userRepository;
    @Inject
    private  PasswordEncoder passwordEncoder;

    @Post
    public HttpResponse<?> register(@Body RegistrationRequest request) {
        if (userRepository.findByUserName(request.getUsername()).isPresent()) {
            return HttpResponse.status(HttpStatus.CONFLICT).body("Username already taken");
        }
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User newUser = new User(request.getUsername(), hashedPassword);
        userRepository.saveUser(newUser);

        return HttpResponse.created("User registered successfully");
    }
}