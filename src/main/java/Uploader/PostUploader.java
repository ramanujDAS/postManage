package Uploader;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PostUploader {

    private final List<SocialMediaUploader> socialMediaUploader; // <1>

    public PostUploader(List<SocialMediaUploader> uploader) {
        this.socialMediaUploader = new ArrayList<>(uploader);
    }

    public boolean uploadPost(PostAttributes postAttributes) {
        for (SocialMediaUploader uploader : socialMediaUploader) {
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

