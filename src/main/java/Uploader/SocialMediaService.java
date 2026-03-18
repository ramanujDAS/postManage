package Uploader;

import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
@Slf4j
public class SocialMediaService {
    private final Map<String, SocialMediaUploader> uploaders;

    public SocialMediaService(List<SocialMediaUploader> uploaderList) {
        this.uploaders = uploaderList.stream()
            .collect(Collectors.toMap(SocialMediaUploader::getPlatformName, u -> u));
    }

    public String uploadToAll(SocialMediaPost post) {
        return uploaders.values().stream()
            .map(uploader -> uploader.upload(post))
            .collect(Collectors.joining(", "));
    }

    public String uploadToPlatform(String platform, SocialMediaPost post) {
        SocialMediaUploader uploader = uploaders.get(platform.toLowerCase());
        if (uploader == null) {
            throw new IllegalArgumentException("Platform not supported: " + platform);
        }
        return uploader.upload(post);
    }
}
