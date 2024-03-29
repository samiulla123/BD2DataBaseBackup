import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

class Main {

    private static String USER_NAME = "abrarkhanpathan74@gmail.com";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "9738544546"; // GMail password
    private static String RECIPIENT = "samipat@sgibgm.org";
	
    public static void main(String[] args) {
		System.out.println("Mail Service");
        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = { RECIPIENT }; // list of recipient email addresses
        String subject = "Database Backup";
        String body = "DataBase Is Backup Sucssully!";

        sendFromGMail(from, pass, to, subject, body);
    }

    private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
		System.out.println("Send Mail Data");
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
			System.out.println("In Try Statment");
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
			System.out.println("Address Exception");
            ae.printStackTrace();
        }
        catch (MessagingException me) {
			System.out.println("Messaging "+me);
            me.printStackTrace();
        }
    }
}