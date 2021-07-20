package com.wzu.lrz.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendSimpleMail(){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setSubject("登陆提示");
        message.setFrom("2279834939@qq.com");
        message.setTo("2279834939@qq.com");
        message.setSentDate(new Date());
        message.setText("您的账号在"+new Date()+"成功登录lrz超市后台管理系统");

        javaMailSender.send(message);
    }
    public void sendComplexMail() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setSubject("通知");
        helper.setText("<b style='color:red'>记得开会</b>",true);

        //发送附件
        helper.addAttachment("可达鸭.jpg", new File("D:\\桌面\\图库\\可达鸭.jpg"));

        helper.setTo("2279834939@qq.com");
        helper.setFrom("2279834939@qq.com");

        javaMailSender.send(mimeMessage);
    }
}
