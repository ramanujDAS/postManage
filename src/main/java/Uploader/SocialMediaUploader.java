package Uploader;

public interface SocialMediaUploader {
    boolean isPostValid(PostAttributes postAttributes);

    boolean uploadPost(PostAttributes  postAttributes);
}
