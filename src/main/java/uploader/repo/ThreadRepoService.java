package uploader.repo;

import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class ThreadRepoService implements IRepoService{

    private static String accessToken = "THAAN20SjRTpBBUVNuTlBlRWU0SldLUTZAtb0VVenM0SVVUVVdWaVNkcl84N2FyanQ0cVpPcDJmQzZAYdlpIWW5jVzBuWmQtVXoyc005Y2NrVy1ISjNkTlA1bktlbzZACY29pejNWVF9TRkRDTFU3WkN3Q1RjSjlfeGluNHVWQWVVZAHlTc2N3a2xRME96TWRmV2FrNnVLa3k1ZATkzTmF5TU53T2JSY2JwdwZDZD";
    private static String contentId ="";
    @Override
    public String getToken(String customerNo) {
        return accessToken;
    }

    @Override
    public String saveContentId(String customer, String contentId) {
        log.info("storing contentId for customer={}",customer);
        ThreadRepoService.contentId = contentId;
        return contentId;
    }

    @Override
    public String getContentId(String requestID, String customerNo) {
        log.info("getting contentId for customer={}",customerNo);

        return contentId;
    }

    @Override
    public String saveContentIdAfterPost(String customer, String contentId) {
        log.info("storing contentId after post for customer={}",customer);
        this.contentId = contentId;
        return this.contentId;
    }
}
