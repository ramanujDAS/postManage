package auth.repo;


import auth.User;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


@Singleton
@Slf4j
public class UserRepository {


    private final DataSource dataSource;
    private final int defaultLimit = 5;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Transactional
    public Optional<User> findByUser(String userName) {

        String query = "SELECT username ,password,email FROM customer WHERE username = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, userName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserName(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setEmailId(rs.getString("email"));
                    log.info("user details {}" ,user );
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error during select", e);
        }
        log.info("user details is empty"  );

        return Optional.empty();

    }

    @Transactional
    public Optional<User> findByEmail(String emailID) {

        String query = "SELECT username ,password,email,uuid_identifier FROM customer WHERE email = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, emailID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserName(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setEmailId(rs.getString("email"));
                    user.setUuid(rs.getString("uuid_identifier"));
                    log.info("user details {}" ,user );
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error during select", e);
        }
        log.info("user details is empty");

        return Optional.empty();

    }


    @Transactional
    public boolean saveUser(String userName, String password, String emailId, String mobileNo) {
        String query = "INSERT INTO customer (username, password, email, mobile , user_limit) VALUES (?, ?,? ,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, userName);
            ps.setString(2, password);
            ps.setString(3, emailId);
            ps.setString(4, mobileNo);
            ps.setString(5, String.valueOf(defaultLimit));

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Database error during insert", e);
        }

    }

    @Transactional
    public boolean updateLimit(String email) {
        String query = "UPDATE customer SET user_limit = user_limit - 1 WHERE email = ? AND user_limit > 0;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, email);
            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;


        } catch (SQLException e) {
            throw new RuntimeException("Database error during insert", e);
        }

    }

    @Transactional
    public boolean updateLimitByAdmin(String emailId) {
        String query = "UPDATE customer SET user_limit = ? WHERE email = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, String.valueOf(defaultLimit));
            ps.setString(2, emailId);
            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;


        } catch (SQLException e) {
            throw new RuntimeException("Database error during insert", e);
        }

    }

    @Transactional
    public int getUserLimit(String emailid) {
        String query = "SELECT user_limit from customer  WHERE email = ?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, emailid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("user_limit");
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException("Database error during insert", e);
        }
       return 0;

    }

    @Transactional
    public String getUuidByUser(String email) {

        String query = "SELECT uuid_identifier from customer  WHERE email = ?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("uuid_identifier");
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException("Database error during insert", e);
        }
       return null;

    }
}
