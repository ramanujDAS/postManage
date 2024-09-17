package Uploader;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;


@Singleton
public class PostManage {

   private final PostUploader postUploader;

   @Inject
   private  SocialMediaConfig config;

   public PostManage() {
      this.postUploader = new PostUploader(getSocialMediaUploaders());
   }

   public void uploadPost(List<Post> postAttributes) {
      postAttributes.forEach(postUploader::uploadPost);
   }

   public int getCountByUser(String userName) {
      return 0;
   }
   public int getCountByHashTag(String hashTag) {
       return 0;
   }
   public int getCountByLocation(String location) {
       return 0;
   }

   public int getCountBySocialMedia(String socialMedia) {
       return 0;
   }


    private ArrayList<ISocialMediaUploader> getSocialMediaUploaders() {
      ArrayList<ISocialMediaUploader> socialMediaUploader = new ArrayList<>();

       if (config != null && config.getPlatforms() == null) {
          throw new RuntimeException("Invalid Social Media");
       }
        assert config != null;

        config.getPlatforms().forEach(platform -> {
         switch (SocialMedia.valueOf(platform.getName())) {
            case FACEBOOK:
               socialMediaUploader.add(new FaceBookUpload());
            case INSTAGRAM:
               socialMediaUploader.add(new InstagramUpload());
            default:
               throw new RuntimeException("Invalid Social Media");
         }
      });
      return socialMediaUploader;
   }

   }



