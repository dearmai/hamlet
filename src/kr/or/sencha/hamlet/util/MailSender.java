package kr.or.sencha.hamlet.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSender {
	
	private static final boolean DEBUG_MODE = false;

	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
	private static final String SMTP_PORT = "465";
	private static final String emailMsgTxt = "Gmail SMTP 서버를 사용한 JavaMail 테스트";
	private static final String emailSubjectTxt = "Gmail SMTP 테스트";
	private static final String emailFromAddress = "hamlet@sencha.or.kr";
	private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	private static final String[] sendTo = { "jongkwang@w3labs.kr", "kim@jongkwang.com" };

	public static void main(String args[]) throws Exception {

		// TODO 이거 없어도 잘 발송 되는데??
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

		new MailSender().sendSSLMessage(sendTo, emailSubjectTxt, emailMsgTxt,
				emailFromAddress, "");
		System.out.println("Sucessfully Sent mail to All Users");
	}

	public void sendSSLMessage(String recipients[], String subject, String message, String from, String imageFile) throws MessagingException, UnsupportedEncodingException {
		
		System.out.println( "@@@ recipients[0] : " + recipients[0] );
		System.out.println( "@@@ subject : " + subject );
		System.out.println( "@@@ message : " + message );
		System.out.println( "@@@ from : " + from );

		Properties props = new Properties();
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", DEBUG_MODE);
		props.put("mail.smtp.port", SMTP_PORT);
		props.put("mail.smtp.socketFactory.port", SMTP_PORT);
		props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.put("mail.smtp.socketFactory.fallback", "false");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {

					protected PasswordAuthentication getPasswordAuthentication() {
						PropertiesManager propertiesManager = new PropertiesManager();
						return new PasswordAuthentication(propertiesManager.getKey("mail.sender.id"), propertiesManager.getKey("mail.sencha.pw"));
					}
				});

		session.setDebug(DEBUG_MODE);

		Message msg = new MimeMessage(session);
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);

		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			addressTo[i] = new InternetAddress(recipients[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);

		// Setting the Subject and Content Type
		msg.setSubject(subject);

		/*
		 * 텍스트만 전송하는 경우 아래의 2라인만 추가하면 된다. 그러나 텍스트와 첨부파일을 함께 전송하는 경우에는 아래의 2라인을
		 * 제거하고 대신에 그 아래의 모든 문장을 추가해야 한다.
		 */

		// msg.setContent(message, "text/plain;charset=KSC5601");
		// Transport.send(msg);

		/* 텍스트와 첨부파일을 함께 전송하는 경우에는 위의 2라인을 제거하고 아래의 모든 라인을 추가한다. */
		// Create the message part
		BodyPart messageBodyPart = new MimeBodyPart();

		// Fill the message
		messageBodyPart.setText(" 서버 테스트에 실패하였습니다.\n 실패 시점의 화면을 캡쳐하여 파일첨부 하였습니다.");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// Part two is attachment
		messageBodyPart = new MimeBodyPart();
		File file = new File(imageFile);
		FileDataSource fds = new FileDataSource(file);
		messageBodyPart.setDataHandler(new DataHandler(fds));

		String fileName = fds.getName(); // 한글파일명은 영문으로 인코딩해야 첨부가 된다.
		fileName = new String(fileName.getBytes("UTF-8"), "8859_1");
		messageBodyPart.setFileName(fileName);

		multipart.addBodyPart(messageBodyPart);

		// Put parts in message
		msg.setContent(multipart);

		// Send the message
		Transport.send(msg);

		System.out.println("E-mail successfully sent!!");
	}
}