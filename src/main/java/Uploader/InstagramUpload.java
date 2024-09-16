package Uploader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InstagramUpload implements SocialMediaUploader {


    @Override
    public boolean isPostValid(PostAttributes postAttributes) {
        return false;
    }

    @Override
    public boolean uploadPost(PostAttributes postAttributes) {
        log.info("uploading to Instagram  {}", postAttributes);


        return false;
    }
}
