package auth;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import lombok.*;

@Setter
@Getter
@Introspected
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @NonNull
    private String userName;
    @NonNull
    private String password;
    @NonNull
    private String emailId;
    private String uuid;
}
