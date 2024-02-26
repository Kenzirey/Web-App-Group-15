package no.ntnu.service;

import no.ntnu.database.DatabaseManager;
import no.ntnu.securityutils.SecurityUtils;

import org.springframework.stereotype.Service;
import java.sql.SQLException;
import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Base32;

@Service
public class UserService {

    private final DatabaseManager databaseManager;

    public UserService(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    /**
     * Saves a user's 2FA secret key in the database.
     * @param username The username of the user.
     * @param secretKey The 2FA secret key to save.
     * @throws SQLException If an SQL exception occurs.
     */
    public void saveUser2FASecretKey(String username, String secretKey) throws SQLException {
        databaseManager.saveUser2FASecretKey(username, secretKey);
    }
    
    /**
     * Retrieves a user's 2FA secret key from the database.
     * @param username The username of the user.
     * @return The 2FA secret key.
     * @throws SQLException If an SQL exception occurs.
     */

    public String get2FASecretKey(String username) throws SQLException {
        return databaseManager.get2FASecretKey(username);
    }

    /**
     * Enables or disables 2FA for a user.
     * @param username The username of the user.
     * @param isEnabled True to enable 2FA, false to disable.
     * @throws SQLException If an SQL exception occurs.
     */
    public void set2FAEnabled(String username, boolean isEnabled) throws SQLException {
        databaseManager.set2FAEnabled(username, isEnabled);
    }

    /**
     * Checks if 2FA is enabled for a user.
     * @param username The username of the user.
     * @return True if 2FA is enabled, false otherwise.
     * @throws SQLException If an SQL exception occurs.
     */
    public boolean get2FAEnabled(String username) throws SQLException {
        return databaseManager.get2FAEnabled(username);
    }

    /**
     * Generates a new 2FA secret key for a user and saves it in the database.
     * @param username The username of the user.
     * @return The generated 2FA secret key.
     */

    public String generate2FASecretKey(String username) {
    String secretKey = SecurityUtils.generate2FASecretKey();
    try {
        saveUser2FASecretKey(username, secretKey);
    } catch (SQLException e) {
        throw new RuntimeException("Failed to save 2FA secret key for " + username, e);
    }
    return secretKey;
}

    /**
     * Generates a QR code URL for a user's 2FA setup using their secret key.
     * @param username The username of the user.
     * @param secretKey The 2FA secret key of the user.
     * @return The URL for the QR code to scan.
     */

    public String getQRCodeUrl(String username, String secretKey) {
        String issuer = "web-app-group-15";
        return String.format("otpauth://totp/%s?secret=%s&issuer=%s", username, secretKey, issuer);
    }

    /**
     * Verifies a 2FA token against a user's secret key.
     * @param token The 2FA token to verify.
     * @param secretKey The 2FA secret key of the user.
     * @return True if the token is valid, false otherwise.
     */
    public boolean verify2FAToken(String token, String secretKey) {
        Totp totp = new Totp(secretKey);
        return totp.verify(token);
    }
}