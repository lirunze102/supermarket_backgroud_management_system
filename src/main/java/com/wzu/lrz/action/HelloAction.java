package com.wzu.lrz.action;

import com.wzu.lrz.Service.AsyncService;
import com.wzu.lrz.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.mail.MessagingException;

@Controller
public class HelloAction {

    @Autowired
    AsyncService asyncService;

    @Autowired
    EmailService emailService;

    @GetMapping("/hello")
    public String hello(){
        asyncService.asynSendEmail();
        return "hello";
    }

    @GetMapping("/sendMail")
    public String sendMail(){
        emailService.sendSimpleMail();
        return "email";
    }

    @GetMapping("/sendComMail")
    public String semdComMail() throws MessagingException {
        emailService.sendComplexMail();
        return "email";
    }
}
