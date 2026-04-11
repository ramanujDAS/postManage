package uploader.repo;


import io.micronaut.core.util.StringUtils;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class InstagramRepoService implements IRepoService{


    private String contentID ;

    @Override
    public String getToken(String customerNo){

        return "IGAAcNnejle1tBZAGEtVUt1WmViZAThiLWhuQmQxelRITkVnWGR4YS1jQ0F5dGxGUjQ4LVl1S2h1dUpXSTNOTFN5MldNdjZArdEZATc1ZAtbGVQdDJDbmNxMWRUdlUzbjhSeHYxMDhYNmtWNi1DS3BOZAzB3ZAFhHVEFQQi1GSG5qRXUzSQZDZD";
    }

    @Override
    public String saveContentId(String customerNO ,String contentId) {

        if (StringUtils.isEmpty(contentId)) {
        log.error("contentId is missing in instagram post response");
        }
        this.contentID = contentId;
        return "1";
    }

    @Override
    public String getContentId(String customerNo,String requestId) {
        return contentID;
    }

    @Override
    public String saveContentIdAfterPost(String customer, String contentId) {
        return "1";
    }

}
