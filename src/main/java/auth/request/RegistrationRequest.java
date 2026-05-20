package auth.request;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import lombok.Getter;
import lombok.Setter;

@Introspected
@Getter
@Setter
public class RegistrationRequest {
    @NonNull
    String userName;
    @NonNull
    String password;
    @NonNull
    String emailId;
}

