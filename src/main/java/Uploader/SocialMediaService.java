package Uploader;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
@Slf4j
public class SocialMediaService {
    private List<SocialMediaUploader> uploaderList;
    @Inject
    UploaderService uploaderService;


    @PostConstruct
    void init() {
        uploaderList.addAll(
                uploaderService.getUploaderServices()
        );
    }

    public boolean uploadToAll(SocialMediaPost post) {
        boolean result = true;
        for (SocialMediaUploader uploader : uploaderList) {
            result = result && uploader.upload(post);
        }
        return result;
    }

    public boolean uploadToPlatform(String platform, SocialMediaPost post) {
        SocialMediaUploader uploader = uploaderList.stream()
                .filter(
                        service -> service.getPlatformName().name().equals(platform))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("platform not found")
                );

        return uploader.upload(post);


    }
}
