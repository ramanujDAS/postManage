package Uploader;

public interface SocialMediaUploader {
    String upload(SocialMediaPost post);
    String getPlatformName();
}
