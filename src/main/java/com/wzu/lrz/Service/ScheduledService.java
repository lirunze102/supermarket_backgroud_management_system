package com.wzu.lrz.Service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Service
public class ScheduledService {
    @Scheduled(cron = "0/5 * * * * ?")
    public void hello(){
        System.out.println("hello 我是李润泽18211170211  "+new Date());
    }



    @Scheduled(cron = "0 0 12 * * ?" )
    public void eating(){
        System.out.println("该吃午饭了");
    }
}
