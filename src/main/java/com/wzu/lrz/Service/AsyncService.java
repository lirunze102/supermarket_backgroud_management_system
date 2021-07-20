package com.wzu.lrz.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Autowired
    EmailService emailService;

    @Async
    public void asynSendEmail(){

        emailService.sendSimpleMail();

        System.out.println("异步发送登录提醒");
    }

}
