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
import lombok.extern.slf4j.Slf4j;

@Controller("/register")
@Secured(SecurityRule.IS_ANONYMOUS)
@Slf4j
public class RegistrationController {

    @Inject
    private UserRepository userRepository;
    @Inject
    private  PasswordEncoder passwordEncoder;

    @Post
    public HttpResponse<?> register(@Body RegistrationRequest request) {
        log.info("regustration {}" , request);
        if (userRepository.findByUserName(request.getUserName()).isPresent()) {
            return HttpResponse.status(HttpStatus.CONFLICT).body("Username already taken");
        }
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User newUser = new User(request.getUserName(), hashedPassword, request.getEmailId());
        userRepository.saveUser(newUser);

        return HttpResponse.status(HttpStatus.CREATED);
    }
}