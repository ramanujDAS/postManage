package Uploader;

public interface SocialMediaUploader {
    boolean isFileValid(String filePath);

    boolean uploadFile(String filePath);
}
