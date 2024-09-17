package Uploader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InstagramUpload implements ISocialMediaUploader {


    @Override
    public boolean isPostValid(Post postAttributes) {
        return false;
    }

    @Override
    public boolean uploadPost(Post postAttributes) {
        log.info("uploading to Instagram  {}", postAttributes);


        return false;
    }
}
