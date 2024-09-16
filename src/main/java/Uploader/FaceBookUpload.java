package Uploader;

public class FaceBookUpload implements SocialMediaUploader {
    @Override
    public boolean isPostValid(PostAttributes postAttribute) {
        return false;
    }

    @Override
    public boolean uploadPost(PostAttributes postAttributes) {
        return false;
    }
}
