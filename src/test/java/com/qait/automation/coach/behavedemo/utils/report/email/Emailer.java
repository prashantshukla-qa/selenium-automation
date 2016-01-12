/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation.coach.behavedemo.utils.report.email;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.qait.automation.utils.ConfigPropertyReader;

/**
 *
 * @author prashant.shukla
 */
public class Emailer {

    final String host = "smtp.gmail.com";
    final int port = 25;
    final String from = "automation.resultsqait@gmail.com";
    final String password = "QaitAutomation";

    String jiraStoryId;
    String mailType;
    String recepenientType;

    public Emailer(String jiraStoryId, String mailType, String recepientType) {
        this.jiraStoryId = jiraStoryId;
        this.mailType = mailType;
        this.recepenientType = recepientType;
    }

    public void sendResultsMail() throws MessagingException, IOException {

        Message message = new MimeMessage(getSession());
        message.addFrom(new InternetAddress[]{(new InternetAddress(from))});
        setMailRecipient(message);
        message.setSubject(setMailSubject());
        message.setContent("Automail", "text/html");
        Session session = getSession();
        Transport transport = session.getTransport("smtps");
        transport.connect(host, from, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    private String setMailSubject() {
        String returnSubject = "";
        if (this.mailType.equalsIgnoreCase("nojbehavestory")) {
            returnSubject = "URGENT! " + this.jiraStoryId
                    + " has no acceptance scenario. Please add!";
        } else if (this.mailType.equalsIgnoreCase("pendingsteps")) {
            returnSubject = "URGENT! "
                    + this.jiraStoryId
                    + " has keywords in acceptance tests that require implementation!";
        } else if (this.mailType.equalsIgnoreCase("failure")) {
            returnSubject = "URGENT! " + this.jiraStoryId
                    + " has Test Failures!";
        }
        return returnSubject;
    }

    private Session getSession() {
        Authenticator authenticator = new Authenticator(this.from,
                this.password);
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtps");
        properties.put("mail.smtps.auth", "true");
        properties.setProperty("mail.smtp.submitter", authenticator
                .getPasswordAuthentication().getUserName());
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", String.valueOf(port));
        return Session.getInstance(properties, authenticator);
    }

    private Message setMailRecipient(Message message) throws AddressException,
            MessagingException, IOException {

        String[] recepientlist = ConfigPropertyReader.getProperty(
                this.recepenientType).split(",");
        for (String recepient : recepientlist) {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    recepient.trim()));
        }
        return message;
    }
}
