package Uploader;

import io.micronaut.context.annotation.ConfigurationProperties;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@Singleton
@ConfigurationProperties("socialMediaUploader")
public class SocialMediaConfig {

    @NotNull
    private List<Platform> platforms;

    @Setter
    @Getter
    public static class Platform {
        private String name;
        private Credentials credentials;

    }

    @Setter
    @Getter
    public static class Credentials {
        private String userName;
        private String password;
        private String clientId;

    }
}
