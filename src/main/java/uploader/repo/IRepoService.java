package uploader.repo;

public interface IRepoService {

    String getToken(String customerNo);

    String saveContentId(String customer,String contentId);

    String getContentId(String requestID,String customerNo);

    String saveContentIdAfterPost(String customer , String contentId);

}
