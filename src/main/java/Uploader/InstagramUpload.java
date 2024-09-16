package Uploader;

public class InstagramUpload implements SocialMediaUploader {

    final private String uploadPath;

    public InstagramUpload(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    @Override
    public boolean isPostValid(PostAttributes postAttributes) {
        return false;
    }

    @Override
    public boolean uploadPost(PostAttributes postAttributes) {
        return false;
    }
}
