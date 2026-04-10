package uploader.repo;


import jakarta.inject.Singleton;

@Singleton
public class InstgramRepoService  implements IRepoService{

    public String getToken(String customerNo){

        return "token";
    }
}
