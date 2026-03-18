package auth;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.*;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

@Singleton
public class AuthenticationProviderUserPassword implements AuthenticationProvider {

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Flowable.create(emitter -> {
            String username = authenticationRequest.getIdentity().toString();
            String password = authenticationRequest.getSecret().toString();
            
            if (username.equals("admin") && password.equals("password")) {
                emitter.onNext(AuthenticationResponse.success(username));
                emitter.onComplete();
            } else {
                emitter.onError(AuthenticationResponse.exception());
            }
        },
                BackpressureStrategy.ERROR);
    }
}
