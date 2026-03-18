package Uploader;

import io.micronaut.http.client.HttpClient;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class FacebookUploader implements SocialMediaUploader {
    private final HttpClient httpClient;

    public FacebookUploader(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public String upload(SocialMediaPost post) {
        log.info("Uploading to Facebook: {}", post.getContent());
        // Facebook Graph API implementation
        return "Facebook post uploaded successfully";
    }

    @Override
    public String getPlatformName() {
        return "facebook";
    }
}
