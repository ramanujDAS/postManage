package uploader;


import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import uploader.repo.IRepoService;
import uploader.repo.RepoFactory;
import vendorclient.instagram.InstagramClient;
import vendorclient.instagram.InstagramRequest;

@Singleton
@Slf4j
public class InstagramUploader implements SocialMediaUploader {


    @Inject
    InstagramClient instagramClient;

    @Inject
    RepoFactory repoFactory;


    @Override
    public boolean upload(SocialMediaPost post) {
        try {
            log.info("Uploading to Instagram: {}", post.getContent());

            IRepoService repoService = repoFactory.getRepoService(PlatForm.INSTAGRAM);
            String accessToken = repoService.getToken(post.getCustomerNo());
            instagramClient.upload(getReq(post), accessToken);
        } catch (Exception e) {
            log.error("some error in posting instagram post {}", post.getCustomerNo());
            return false;
        }


        return true;
    }

    @Override
    public PlatForm getPlatformName() {
        return PlatForm.INSTAGRAM;
    }

    private InstagramRequest getReq(SocialMediaPost post) {
        return new InstagramRequest();
    }
}
