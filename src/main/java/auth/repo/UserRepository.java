package auth.repo;


import auth.User;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


@Singleton
public class UserRepository  {


    private final DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Transactional
    public Optional<User> findByUser(String userName){

        String query ="SELECT username ,password FROM customer WHERE username = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, userName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserName(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error during select", e);
        }
        return Optional.empty();

    }


    @Transactional
    public boolean saveUser(String userName , String password , String emailId , String mobileNo){
        String query ="INSERT INTO customer (username, password, email, mobile) VALUES (?, ?,? ,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, userName);
            ps.setString(2, password);
            ps.setString(3, emailId);
            ps.setString(4, mobileNo);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Database error during insert", e);
        }

    }



}
