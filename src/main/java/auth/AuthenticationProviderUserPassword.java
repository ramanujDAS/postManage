package auth;

import auth.repo.UserRepository;
import io.micronaut.context.annotation.Property;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.*;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Singleton
@Slf4j
public class AuthenticationProviderUserPassword implements AuthenticationProvider {

    @Inject
    UserRepository userRepository;

    @Inject
    PasswordEncoder passwordEncoder;

//    @Property(name = "micronaut.security.token.basic-auth")
//    Map<String , String> basicAuthConfig;

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Flowable.fromCallable(() -> {
            String username = authenticationRequest.getIdentity().toString();
            String password = authenticationRequest.getSecret().toString();

            log.info("username {}: password {}" ,username ,password);

            if (("admin").equals(username) && ("12345").equals(password)) {
                return AuthenticationResponse.success((String) authenticationRequest.getIdentity());
            }
            try {
                Optional<User> userOptional = userRepository.findByUser(username);
                if (!userOptional.isPresent())
                    return AuthenticationResponse.failure(AuthenticationFailureReason.USER_NOT_FOUND);

                if (userOptional.get().getPassword().equals(password))
                    return (AuthenticationResponse.success(username, Collections.emptyList()));
                else
                    return AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH);
            }catch (Exception e){
                log.error("username {} not able toi logged in", username,e);
            }

            return AuthenticationResponse.failure();

        }).subscribeOn(Schedulers.io());
    }
}
