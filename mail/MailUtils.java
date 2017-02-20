package cn.xiedacon.utlis;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.junit.Test;

public class MailUtils {

	public static void sendMail(String code, String to, String[] ccs, String[] bccs, InputStream resource) {
		Properties props = new Properties();
		try {
			// 加载配置文件
			props.load(resource);
			// 获取发件人
			final String username = props.getProperty("username");
			// 获取密码
			final String password = props.getProperty("password");
			// 获取session
			Session session = Session.getInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
			// 获取邮件对象
			Message message = new MimeMessage(session);
			// 设置发件人
			if (to == null) {
				throw new RuntimeException("收件人不能为空！");
			}
			message.setFrom(new InternetAddress(username));
			// 设置收送人
			// 设置收件
			message.addRecipient(RecipientType.TO, new InternetAddress(to));
			// 设置抄送
			addRecipient(ccs, RecipientType.CC, message);
			// 设置暗送
			addRecipient(bccs, RecipientType.BCC, message);
			// 设置标题
			message.setSubject(props.getProperty("subject"));
			// 获取正文
			String content = MessageFormat.format(props.getProperty("content"), props.getProperty("callback"), code);
			// 设置正文
			message.setContent(content, "text/html;charset=UTF-8");
			// 发送邮件
			Transport.send(message);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void sendMail(String code, String to, String[] ccs, InputStream resource) {
		sendMail(code, to, ccs, null, resource);
	}

	public static void sendMail(String code, String to, InputStream resource) {
		sendMail(code, to, null, null, resource);
	}

	private static void addRecipient(String[] rs, javax.mail.Message.RecipientType cc, Message message) throws AddressException, MessagingException {
		if (rs == null || rs.length == 0) {
			return;
		}
		for (int i = 0; i < rs.length; i++) {
			message.addRecipient(cc, new InternetAddress(rs[i]));
		}
	}

}
