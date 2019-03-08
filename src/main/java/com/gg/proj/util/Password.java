package com.gg.proj.util;

import org.mindrot.jbcrypt.BCrypt;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * This implementation is inspired by this one https://gist.github.com/craSH/5217757 from Ian Gallagher.
 * It use the JBCrypt 0.4 dependency set in the pom.xml.
 */
public class Password {

    // This is the the log2 of the number of rounds of hashing to apply
    private static final int WORKLOAD = 12;

    /**
     * This method generate a salt, then hash the password using OpenBSD BCrypt.
     *
     * @param plaintextPassword the password to hash
     * @return String containing the hashed password
     */
    public static String hashPassword(String plaintextPassword) {
        String salt = BCrypt.gensalt(WORKLOAD);
        return BCrypt.hashpw(plaintextPassword, salt);
    }

    /**
     * This method first check the given hash. We check if the password start with $2a$10$,
     * which indicates the algorithm used is BCrypt.
     * <p>
     * Then it use the checkpw method that check if the password and the hash match.
     *
     * @param plaintextPassword the password provided by the login request.
     * @param storedHash        the account password hash.
     * @return a boolean : true if the check match, false if not.
     * @throws IllegalArgumentException on invalid stored hash.
     */
    public static boolean checkPassword(String plaintextPassword, String storedHash) throws IllegalArgumentException {

        if (storedHash == null || !storedHash.startsWith("$2a$"))
            throw new IllegalArgumentException("The given hash is not valid");
        LocalDateTime start = LocalDateTime.now();
        boolean isOkay = BCrypt.checkpw(plaintextPassword, storedHash);
        LocalDateTime end = LocalDateTime.now();

        Duration total = Duration.between(end, start);
        System.out.println(total.toString());

        return isOkay;
    }
}
