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

    public int getUserLimit(String userName){

       return userRepository.getUserLimit(userName);
    }

    public int updateUserLimit(String name) {

        boolean isUpdate = userRepository.updateLimit(name);
        return 1;
    }
    public boolean updateUserLimitBYAdmin(String name){
        boolean isUpdated = userRepository.updateLimitByAdmin(name);

        return isUpdated;
    }
}
