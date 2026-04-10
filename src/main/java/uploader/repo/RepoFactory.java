package uploader.repo;

import io.micronaut.context.BeanContext;
import io.micronaut.context.annotation.Bean;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import uploader.PlatForm;

import java.lang.invoke.SwitchPoint;

@Singleton
public class RepoFactory {


    @Inject
    BeanContext beanContext;


   public IRepoService getRepoService(PlatForm platForm) {

        switch (platForm) {
            case INSTAGRAM:
                return beanContext.getBean(InstgramRepoService.class);
            default:
                throw new IllegalArgumentException();
        }
    }

}
