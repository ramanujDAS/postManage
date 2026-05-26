package auth;


import auth.repo.UserRepository;
import auth.request.RegistrationRequest;
import io.micronaut.core.util.StringUtils;
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
        if (userRepository.findByUser(request.getUserName()).isPresent()) {
            return HttpResponse.status(HttpStatus.CONFLICT).body("Username already taken");
        }
        ///String hashedPassword = passwordEncoder.encode(request.getPassword());

        User newUser = new User(request.getUserName(), request.getPassword(), request.getEmailId());
        userRepository.saveUser(newUser.getUserName(), newUser.getPassword() , newUser.getEmailId() , StringUtils.EMPTY_STRING);

        return HttpResponse.status(HttpStatus.CREATED);
    }
}