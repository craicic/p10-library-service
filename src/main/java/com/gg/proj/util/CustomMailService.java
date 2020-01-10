package com.gg.proj.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 *
 * This class manage mail sending
 */
@Component
public class CustomMailService {

    private JavaMailSender mailSender;

    @Autowired
    public CustomMailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * <p>Sends an Email to the following recipient using {@link StringBuilder} to write mail, and {@link JavaMailSender}
     * to send it.</p>
     *
     * @param recipient mail recipient
     * @param firstName the recipient name
     * @param lastName the recipient surname
     * @param bookName the booked book
     * @param libraryName the library
     */
    public void sendSimpleMail(String recipient, String firstName, String lastName, String bookName, String libraryName) {

        StringBuilder text = new StringBuilder();
        SimpleMailMessage message = new SimpleMailMessage();

        // Create a message
        text.append("Hello ").append(firstName).append(" ").append(lastName).append("\n")
                .append("The item you booked : ").append(bookName).append(" has been returned to the following library : ")
                .append(libraryName).append(". You now have 48hour to claim it.");
        message.setTo(recipient);
        message.setText(text.toString());
        message.setSubject("[INFO] Booking info.");

        // Send mail
        mailSender.send(message);
    }
}
