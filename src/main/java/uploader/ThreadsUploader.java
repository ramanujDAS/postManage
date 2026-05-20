package uploader;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import uploader.repo.IRepoService;
import uploader.repo.RepoFactory;
import vendorclient.thread.ThreadClient;
import vendorclient.thread.model.ThreadPublishRequest;
import vendorclient.thread.model.ThreadPublishResponse;
import vendorclient.thread.model.ThreadUploadReqest;
import vendorclient.thread.model.ThreadUploadResponse;

import java.util.Optional;

@Singleton
@Slf4j
public class ThreadsUploader implements SocialMediaUploader {

    @Inject
    ThreadClient threadClient;

    @Inject
    RepoFactory repoFactory;

    @Override
    public boolean upload(SocialMediaPost post) {
        try {
            log.info("Uploading to Threads: {}", post);

            IRepoService repoService = repoFactory.getRepoService(PlatForm.THREAD);
            String accessToken = repoService.getToken(post.getCustomerNo());
            Optional<ThreadUploadResponse> threadUploadResponseOptional = threadClient.upload(getReq(post), accessToken);

            if (!threadUploadResponseOptional.isPresent()) {
                log.error("threads response not found while posting to Threads for customer: {}", post.getCustomerNo());
                return false;
            }

            repoService.saveContentId(post.getCustomerNo(), threadUploadResponseOptional.get().getContentId());

            String contentID = repoService.getContentId(post.getCustomerNo(), post.getRequestId());
            ThreadPublishRequest threadPublishRequest = new ThreadPublishRequest();
            threadPublishRequest.setContentId(contentID);

            Optional<ThreadPublishResponse> opt = threadClient.postContent(threadPublishRequest, accessToken);
            if (!opt.isPresent()) {
                log.error("threads response not found while posting to Threads for customer: {}", post.getCustomerNo());
                return false;
            }
            repoService.saveContentIdAfterPost(post.getCustomerNo(), opt.get().getPostedId());

        } catch (Exception e) {
            log.error("some error in posting threads post {}", post.getCustomerNo(), e);
            return false;
        }

        return true;
    }

    @Override
    public PlatForm getPlatformName() {
        return PlatForm.THREAD;
    }

    private ThreadUploadReqest getReq(SocialMediaPost post) {
        ThreadUploadReqest request = new ThreadUploadReqest();
        request.setMediaType("IMAGE");
        request.setImageUrl(post.getImageUrl());
        request.setCaptionText(post.getContent());
        return request;
    }
}
