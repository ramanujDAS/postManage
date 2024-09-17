package Uploader;

public interface ISocialMediaUploader {
    boolean isPostValid(Post postAttributes);

    boolean uploadPost(Post postAttributes);
}
