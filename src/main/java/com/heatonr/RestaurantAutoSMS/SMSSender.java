package com.heatonr.RestaurantAutoSMS;

import javafx.application.Platform;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.util.Properties;

public class SMSSender {

    SMSGUI gui;
    final String username = "bambooexpressallendale@gmail.com";
    final String password = "Rlyodd123";
    Properties prop;
    Session session;

    public SMSSender(SMSGUI pGUI) {
        gui = pGUI;

        prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

    }

    public boolean sendMessage(String recipient, String pMessasge) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("bambooexpressemail@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipient + "@mms.att.net, " + recipient + "@pm.sprint.com, " +
                            recipient + "@vzwpix.com, " + recipient + "@tmomail.net, " + recipient +
                            "@myboostmobile.com, " + recipient + "@vmpix.com, " + recipient + "@mms.uscc.net")
            );
            message.setSubject("Bamboo Express Order");
            message.setText(pMessasge);

            Transport.send(message);

            System.out.println("Done");
        }
        catch (AddressException e){
            sendMessage("The previous order was not in the correct format. Order received at "
            + new Timestamp(System.currentTimeMillis()));
            return false;
        }
        catch (MessagingException e) {
            sendMessage(e.getMessage());
        }

        return true;
    }

    public void sendMessage(String message){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gui.printError(message);
            }
        });
    }
}