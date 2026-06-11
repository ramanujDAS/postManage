package auth;


import auth.repo.UserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class LimitService {

    private int userLimit = 5;

    @Inject
    UserRepository userRepository;

    public int getUserLimit(String emailId){

       return userRepository.getUserLimit(emailId);
    }

    public int updateUserLimit(String email) {

        boolean isUpdate = userRepository.updateLimit(email);
        return 1;
    }
    public boolean updateUserLimitBYAdmin(String email){
        boolean isUpdated = userRepository.updateLimitByAdmin(email);

        return isUpdated;
    }

    public String getUuidByUser(String email){
        return userRepository.getUuidByUser(email);
    }
}
