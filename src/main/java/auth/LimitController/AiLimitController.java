package auth.LimitController;

import auth.LimitService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import uploader.SocialMediaPost;

import javax.validation.constraints.NotNull;
import java.security.Principal;

@Controller("/user/limit")
@Secured(SecurityRule.IS_AUTHENTICATED)
@Slf4j
public class AiLimitController {


    @Inject
    LimitService limitService;

    @Get
    public Publisher<? extends HttpResponse<?>> getAIUsedLimit(Principal principal) {
        return Flowable.fromCallable(() -> {
            UserLimit userLimit = new UserLimit();

            log.info("getAIUsedLimit api called for {}",principal.getName());
            userLimit.setUserLimit(limitService.getUserLimit(principal.getName()));

            return HttpResponse.status(HttpStatus.OK).body(userLimit);

        }).subscribeOn(Schedulers.io());
    }

    @Post
    public Publisher<? extends HttpResponse<?>> updateUserLimit(Principal principal) {
        return Flowable.fromCallable(() -> {
            UserLimit userLimit = new UserLimit();
            log.info("update ai use limit api called for {}",principal.getName());
            userLimit.setUserLimit(limitService.updateUserLimit(principal.getName()));

            return HttpResponse.status(HttpStatus.OK).body(userLimit);

        }).subscribeOn(Schedulers.io());
    }

}
