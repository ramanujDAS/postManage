package uploader.repo;

import io.micronaut.context.BeanContext;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import uploader.PlatForm;

@Singleton
public class RepoFactory {


    @Inject
    BeanContext beanContext;


   public IRepoService getRepoService(PlatForm platForm) {

        switch (platForm) {
            case INSTAGRAM:
                return beanContext.getBean(InstagramRepoService.class);
            default:
                throw new IllegalArgumentException();
        }
    }

}
