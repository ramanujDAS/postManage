package Uploader;

public class InstagramUpload implements SocialMediaUploader {

    final private String uploadPath;

    public InstagramUpload(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    @Override
    public boolean isFileValid(String filePath) {
        return false;
    }

    @Override
    public boolean uploadFile(String filePath) {
        return false;
    }
}
