package uploader;

import io.micronaut.context.BeanContext;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Singleton
@Slf4j
public class UploaderService {


    private List<PlatForm> platformList = Arrays.asList(PlatForm.FACEBOOK ,PlatForm.INSTAGRAM ,PlatForm.THREAD ,PlatForm.TWITTER);


    @Inject
    BeanContext beanContext;

    public List<SocialMediaUploader> getUploaderServices() {
        List<SocialMediaUploader> platformServices = new ArrayList<>();
        for (PlatForm platform : platformList) {
            platformServices.add(getService(platform));
        }
        return platformServices;
    }

    private SocialMediaUploader getService(PlatForm platform){
        switch (platform) {
            case THREAD:
                return beanContext.getBean(ThreadsUploader.class);
            case INSTAGRAM:
                return beanContext.getBean(InstagramUploader.class);
            case TWITTER:
                return beanContext.getBean(TwitterUploader.class);
            case FACEBOOK:
                return beanContext.getBean(FacebookUploader.class);
            default:
                 throw  new RuntimeException();
        }

        }
    }

