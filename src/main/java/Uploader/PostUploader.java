package Uploader;

import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Singleton
public class PostUploader {

    private final List<ISocialMediaUploader> socialMediaUploader; // <1>

    public PostUploader(List<ISocialMediaUploader> uploader) {
        this.socialMediaUploader = new ArrayList<>(uploader);
    }

    public boolean uploadPost(Post postAttributes) {
        for (ISocialMediaUploader uploader : socialMediaUploader) {
            try {
                if (uploader.isPostValid(postAttributes)) {
                    return uploader.uploadPost(postAttributes);
                }
            } catch (Exception e) {
                log.info("Error while uploading file : {}  for Uploader : {}", postAttributes, uploader, e);
            }
        }
        return true;
    }
}

