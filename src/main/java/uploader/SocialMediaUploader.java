package uploader;

public interface SocialMediaUploader {
    boolean upload(SocialMediaPost post);
    PlatForm getPlatformName();
}
