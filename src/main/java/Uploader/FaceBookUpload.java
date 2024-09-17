package Uploader;

public class FaceBookUpload implements ISocialMediaUploader {
    @Override
    public boolean isPostValid(Post postAttribute) {
        return false;
    }

    @Override
    public boolean uploadPost(Post postAttributes) {
        return false;
    }
}
