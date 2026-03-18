package Uploader;

import io.micronaut.http.annotation.*;
import lombok.extern.slf4j.Slf4j;

@Controller("/api/social")
@Slf4j
public class SocialMediaController {
    private final SocialMediaService socialMediaService;

    public SocialMediaController(SocialMediaService socialMediaService) {
        this.socialMediaService = socialMediaService;
    }

    @Post("/upload/all")
    public String uploadToAll(@Body SocialMediaPost post) {
        return socialMediaService.uploadToAll(post);
    }

    @Post("/upload/{platform}")
    public String uploadToPlatform(@PathVariable String platform, @Body SocialMediaPost post) {
        return socialMediaService.uploadToPlatform(platform, post);
    }
}
