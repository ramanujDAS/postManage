package uploader.repo;

import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class ThreadRepoService implements IRepoService{

    private static String accessToken = "THAAN20SjRTpBBUVR1RUNsaVRtcFcyQUFaMWhPZADkyZAEdzVkRRVnlKQmlUOXM4dDdpSlRwMVAtYUppM0ZAfYmxKMlFEUk5oODVtS21FaWV2R2hDdjB0X0VyZAjY0aEdWQURSeUIyLXRreFVDQUxUd1J3aFRXT3lKM2RVTmdxUUtjU1JHNVNoc3VYNHhDcDY0M3luOUEwcEhXZAEo2ZAy1za2tjS1FEQXB5UQZDZD";
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
