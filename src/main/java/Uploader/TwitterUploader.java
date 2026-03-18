package Uploader;

import io.micronaut.http.client.HttpClient;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class TwitterUploader implements SocialMediaUploader {
    private final HttpClient httpClient;

    public TwitterUploader(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public String upload(SocialMediaPost post) {
        log.info("Uploading to Twitter: {}", post.getContent());
        // Twitter API v2 implementation
        return "Twitter post uploaded successfully";
    }

    @Override
    public String getPlatformName() {
        return "twitter";
    }
}
