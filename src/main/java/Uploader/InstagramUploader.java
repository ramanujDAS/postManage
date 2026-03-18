package Uploader;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class InstagramUploader implements SocialMediaUploader {
    private final HttpClient httpClient;

    public InstagramUploader(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public String upload(SocialMediaPost post) {
        log.info("Uploading to Instagram: {}", post.getContent());
        // Instagram Graph API implementation
        return "Instagram post uploaded successfully";
    }

    @Override
    public String getPlatformName() {
        return "instagram";
    }
}
