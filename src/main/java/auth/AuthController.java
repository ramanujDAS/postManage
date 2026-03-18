package auth;

import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;

@Controller("/api/auth")
@Secured(SecurityRule.IS_ANONYMOUS)
public class AuthController {

    @Get("/status")
    public String status() {
        return "Auth service is running";
    }
}
