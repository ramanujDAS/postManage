package Uploader;

public class FaceBookUpload implements SocialMediaUploader {
    @Override
    public boolean isFileValid(String filePath) {
        return false;
    }

    @Override
    public boolean uploadFile(String filePath) {
        return false;
    }
}
