package Uploader;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
public class PostUploader {

    private final List<SocialMediaUploader> socialMediaUploader; // <1>

    public PostUploader(List<SocialMediaUploader> uploader) {
        this.socialMediaUploader = new ArrayList<>(uploader);
    }

    public boolean uploadFile(String filePath) {
        for (SocialMediaUploader uploader : socialMediaUploader) {
            try {
                if (uploader.isFileValid(filePath)) {
                    return uploader.uploadFile(filePath);
                }
            } catch (Exception e) {
                log.info("Error while uploading file : {}  for Uploader : {}", filePath, uploader, e);
            }
        }
        return true;
    }
}

