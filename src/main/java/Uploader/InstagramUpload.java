package Uploader;

import jakarta.inject.Inject;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InstagramUpload implements ISocialMediaUploader {



    @Override
    public boolean isPostValid(Post postAttributes) {
        return false;
    }

    @Override
    public boolean uploadPost(Post postAttributes) {
        Credentials credentials = SocialMediaConfig.getCredentials(this.toString());
        log.info("uploading to Instagram  {}", postAttributes);


        return false;
    }

    @Override
    public String toString() {
        return "INSTAGRAM";
    }
}
