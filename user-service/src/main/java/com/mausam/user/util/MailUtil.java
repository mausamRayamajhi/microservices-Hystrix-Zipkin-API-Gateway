package com.mausam.user.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {

//     static String username="EIS.org.Pvt";
//    static String password="eis123456";

    static String username="mausam.raya7@gmail.com";
    static String password="work123,";

	public static void sendEmail(String email,String message_to_user,String message_subject_to_user) 
			throws MessagingException {
		
		Properties props = new Properties();
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        Session session = Session.getInstance(props,auth);
        
   
        
        MimeMessage msg = new MimeMessage(session);
        msg.setContent(message_to_user, "text/html");
        msg.setSubject(message_subject_to_user);
        msg.setFrom(new InternetAddress(username));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        
		Transport transport = session.getTransport("smtps");
		transport.connect("smtp.gmail.com", 465, username, password);
		transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
	}
}
