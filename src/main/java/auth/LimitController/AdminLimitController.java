package auth.LimitController;

import auth.LimitService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;

import java.security.Principal;

@Controller("/admin/limit")
@Secured(SecurityRule.IS_AUTHENTICATED)
@Slf4j
public class AdminLimitController {
    @Inject
    LimitService limitService;

    @Post("/{userName}")
    public Publisher<? extends HttpResponse<?>> updateUserLimit(@PathVariable String userName) {
        return Flowable.fromCallable(() -> {
            log.info("update ai use limit api called byadmin {}", userName);
            boolean isDone = limitService.updateUserLimitBYAdmin(userName);
            return HttpResponse.status(HttpStatus.OK).body(isDone);

        }).subscribeOn(Schedulers.io());
    }
}
