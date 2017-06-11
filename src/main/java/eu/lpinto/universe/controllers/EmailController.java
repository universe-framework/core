package eu.lpinto.universe.controllers;

import java.util.Properties;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * Controller for Emails.
 *
 * @author Vítor Martins <code>- vitor.martins@petuniversal.com</code>
 */
@Stateless
public class EmailController {

    private String senderEmail = "software@petuniversal.com";
    private String senderPassword = "4fdbf14afa3a763f30b42e8ca7ab036b";
    private String SMTP_SSL_TRUST = "*";
    private String IMAP_SSL_TRUST = "*";
    private Boolean SMTP_TLS = true;
    private String SMTP_ADDR = "smtp.petuniversal.com";
    private Integer SMTP_PORT = 587;

    private final Session session;

    public EmailController() {
        Properties props = new Properties();
        props.put("mail.smtp.ssl.trust", SMTP_SSL_TRUST);
        props.put("mail.imaps.ssl.trust", IMAP_SSL_TRUST);
        props.put("mail.smtp.starttls.enable", SMTP_TLS);
        props.put("mail.smtp.host", SMTP_ADDR);
        props.put("mail.smtp.port", SMTP_PORT);

        if (senderEmail == null) {
            session = Session.getInstance(props);

        } else {
            props.put("mail.smtp.auth", "true");
            session = Session.getInstance(props, new javax.mail.Authenticator() {
                                      @Override
                                      protected PasswordAuthentication getPasswordAuthentication() {
                                          return new PasswordAuthentication(senderEmail, senderPassword);
                                      }
                                  });
        }
    }

    public void sendEmail(final String receiverEmail, final String subject, final String emailMessage) {
        sendEmail(senderEmail, senderPassword, receiverEmail, subject, emailMessage);
    }

    public void sendEmail(final String username, final String password, final String receiverEmail, final String subject, final String emailMessage) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
            message.setSubject(subject == null ? senderEmail.split("@")[1] : subject);
            message.setContent(emailMessage == null ? "" : emailMessage, "text/html; charset=UTF-8");
            Transport.send(message);
        }

        catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
