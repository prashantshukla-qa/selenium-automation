package com.qait.automation.coach.behavedemo.utils.report.email;

import javax.mail.PasswordAuthentication;

/**
 *
 * @author prashant
 */
class Authenticator extends javax.mail.Authenticator {

    private PasswordAuthentication authentication;

    public Authenticator(String from, String password) {
	authentication = new PasswordAuthentication(from, password);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
	return authentication;
    }
}
