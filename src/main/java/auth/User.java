package auth;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Introspected
@Builder
@AllArgsConstructor
public class User {

    @NonNull
    private String username;
    @NonNull
    private String password;
}
