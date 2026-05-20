package auth;

import auth.repo.UserRepository;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.*;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

import java.util.Collections;

@Singleton
public class AuthenticationProviderUserPassword implements AuthenticationProvider {

    @Inject
    UserRepository userRepository;

    @Inject
    PasswordEncoder passwordEncoder;

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Flowable.create(emitter -> {
            String username = authenticationRequest.getIdentity().toString();
            String password = authenticationRequest.getSecret().toString();

            userRepository.findByUserName(username)
                    .map(user -> {
                        if (passwordEncoder.matches(password ,user.getPassword())) {
                            emitter.onNext(AuthenticationResponse.success(username, Collections.emptyList()));
                        } else {
                            emitter.onNext(AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH));
                        }
                        return user;
                    })
                    .orElseGet(() -> {
                        emitter.onNext(AuthenticationResponse.failure(AuthenticationFailureReason.USER_NOT_FOUND));
                        return null;
                    });
            emitter.onComplete();
        }, BackpressureStrategy.ERROR);
    }
}
