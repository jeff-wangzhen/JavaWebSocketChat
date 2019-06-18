package mail;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMailText {

	public SendMailText(String nickName, String to, String subject, String content) throws Exception {
		System.out.println(to);
		System.out.println(subject);
		System.out.println(content);
		String host = ""; // smtp主机
		String username = ""; // 认证用户名
		String password = ""; // 认证密码
		String from = ""; // 发送者
		// 建立session
		Properties prop = new Properties();
		prop.put("mail.smtp.host", host);
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.auth", "true"); // 是否需要认证
		Session session = Session.getDefaultInstance(prop, null);
		// 创建MIME邮件对象
		MimeMessage mimeMsg = new MimeMessage(session);
		MimeMultipart mp = new MimeMultipart();

		// 设置信息
		mimeMsg.setFrom(new InternetAddress(from, nickName, "UTF-8"));
		mimeMsg.setSubject(subject, "UTF-8"); // ！！！注意设置编码
		mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

		// 设置正文
		BodyPart body = new MimeBodyPart();
		body.setContent(content, "text/html;charset=UTF-8"); // ！！！注意设置编码
		mp.addBodyPart(body);
		mimeMsg.setContent(mp);

		// 发送邮件
		Transport transport = session.getTransport("smtp");
		transport.connect(host, username, password);
		transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
		transport.close();
	}

}