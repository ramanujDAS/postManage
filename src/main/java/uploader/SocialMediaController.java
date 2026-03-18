package uploader;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;

import javax.validation.constraints.NotNull;
import java.security.Principal;

@Controller("/api/social/")
@Slf4j
@Secured(SecurityRule.IS_AUTHENTICATED)
public class SocialMediaController {

    @Inject
    SocialMediaService socialMediaService;

    @Post("v1/upload/all")
    public Publisher<? extends HttpResponse<Boolean>> uploadToAllPlatform(Principal principal , @NotNull @Body SocialMediaPost post) {
        return Flowable.fromCallable(() -> {
            log.info("uploadToAllPlatform :customer : {}" , principal.getName());
            boolean result = socialMediaService.uploadToAll(post);
            return HttpResponse.ok(result);
        }).subscribeOn(Schedulers.io());
    }

    @Post("v1/upload/{platform}")
    public Publisher<? extends HttpResponse<Boolean>> uploadToPlatform(Principal principal ,@PathVariable String platform, @NotNull @Body SocialMediaPost post) {
        return Flowable.fromCallable(() -> {
            log.info("uploadToPlatform : customer : {}" , principal.getName());
            boolean result = socialMediaService.uploadToPlatform(platform, post);
            return HttpResponse.ok(result);
        }).subscribeOn(Schedulers.io());
    }


}
