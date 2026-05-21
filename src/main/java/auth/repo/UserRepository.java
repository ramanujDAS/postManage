package auth.repo;


import auth.User;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class UserRepository {
    private String userName = "admin";
    private String passWord ="password";
    private String email = "email";

    public Optional<User> findByUserName(String userName){
        if(userName.equals(this.userName))
            return Optional.of(new User(this.userName,this.passWord,this.email));
        return Optional.empty();
    }

    public boolean saveUser(User user){
        this.userName = user.getUserName();
        this.passWord = user.getPassword();
        this.email = user.getEmailId();
        return true;
    }
}
