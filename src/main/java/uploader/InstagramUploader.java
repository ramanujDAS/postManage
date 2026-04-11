package uploader;


import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import uploader.repo.IRepoService;
import uploader.repo.RepoFactory;
import vendorclient.instagram.*;

import java.util.Optional;

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

            //todo  need to put it in cadence like system
            IRepoService repoService = repoFactory.getRepoService(PlatForm.INSTAGRAM);
            String accessToken = repoService.getToken(post.getCustomerNo());
            Optional<InstgramResponse> instagramResponseOpt = instagramClient.upload(getReq(post), accessToken);
            if (!instagramResponseOpt.isPresent()) {
                log.error("instagram response not found while posting to Instagram for customer: {}", post.getCustomerNo());
                throw new IllegalArgumentException("instagram response not found");
            }
            repoService.saveContentId(post.getCustomerNo(), instagramResponseOpt.get().getContentId());

            String contentID = repoService.getContentId(post.getCustomerNo() , post.getRequestId());

            return publishContent(post.getCustomerNo(),contentID);

        } catch (Exception e) {
            log.error("Error occurred while posting to Instagram for customer: {}", post.getCustomerNo());
            return false;
        }

    }

    @Override
    public PlatForm getPlatformName() {
        return PlatForm.INSTAGRAM;
    }

    private InstagramRequest getReq(SocialMediaPost post) {
        StringBuilder caption = new StringBuilder(post.getContent() != null ? post.getContent() : "");
        if (post.getHashTags() != null && !post.getHashTags().isEmpty()) {
            caption.append("\n\n");
            post.getHashTags().forEach(tag -> caption.append("#").append(tag.trim()).append(" "));
        }

        InstagramRequest request = new InstagramRequest();
        request.setCaption(caption.toString());
        request.setImageUrl(post.getImageUrl());
        request.setVideoUrl(post.getVideoUrl());
        return request;

    }


    private boolean publishContent(String customerNo,String contentId ){

        InstagramContentPostRequest postRequest = new InstagramContentPostRequest();
        postRequest.setCreationId(contentId);
        postRequest.setCustomerNo(customerNo);

        try {
            String accessToken = repoFactory
                    .getRepoService(PlatForm.INSTAGRAM)
                    .getToken(customerNo);

            Optional<InstagramContentPostResponse> contentPostResponse = instagramClient.postContent(postRequest, accessToken);
            if (!contentPostResponse.isPresent()) {
                log.error("instagram response not found while content posting to Instagram for customer: {}", customerNo);
                throw new IllegalArgumentException("instagram and contentId in response not found");
            }
            repoFactory
                    .getRepoService(PlatForm.INSTAGRAM)
                    .saveContentIdAfterPost(
                            customerNo,
                            contentPostResponse.get().getId()
                    );

        }catch (Exception e){
            log.error("got some error while posting content to instagram for customer {} contentId = {}",customerNo,contentId);
            return false;
        }
       return true;
    }
}
