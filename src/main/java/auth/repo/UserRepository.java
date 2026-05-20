package auth.repo;


import auth.User;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class UserRepository {
    private String userName = "admin";
    private String passWord ="password";

    public Optional<User> findByUserName(String userName){
        if(this.userName.equals(userName))
            return Optional.of(new User(this.userName,this.passWord));
        return Optional.empty();
    }

    public boolean saveUser(User user){
        this.userName = user.getUsername();
        this.passWord = user.getPassword();
        return true;
    }
}
