package Uploader;

import io.micronaut.http.client.HttpClient;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class ThreadsUploader implements SocialMediaUploader {
    private final HttpClient httpClient;

    public ThreadsUploader(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public String upload(SocialMediaPost post) {
        log.info("Uploading to Threads: {}", post.getContent());
        // Threads API implementation
        return "Threads post uploaded successfully";
    }

    @Override
    public String getPlatformName() {
        return "threads";
    }
}
