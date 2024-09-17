package Uploader;

import io.micronaut.context.annotation.ConfigurationProperties;
import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static Uploader.SocialMedia.*;

@Setter
@Getter
@ConfigurationProperties("socialMediaUploader")
public class SocialMediaConfig {

    private static Uploader.Credentials faceBookCredentials = null;
    private static Uploader.Credentials instagramCredentials = null;

    @NotNull
    private static List<Platform> platforms;

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

    public static  synchronized Uploader.Credentials getCredentials(String socialMedia) {
        switch (SocialMedia.valueOf(socialMedia)) {
            case FACEBOOK: {
                if (faceBookCredentials == null) {
                    faceBookCredentials = new Uploader.Credentials(
                            platforms.get(1).getCredentials().getUserName(),
                            platforms.get(1).getCredentials().getPassword(),
                            platforms.get(1).getCredentials().getClientId());
                }
                return faceBookCredentials;
            }
            case INSTAGRAM: {
                if (instagramCredentials == null) {
                    instagramCredentials = new Uploader.Credentials(
                            platforms.get(0).getCredentials().getUserName(),
                            platforms.get(0).getCredentials().getPassword(),
                            platforms.get(0).getCredentials().getClientId());
                }
                return instagramCredentials;
            }
            default:
                throw new RuntimeException("Invalid Social Media");

        }
    }




}
