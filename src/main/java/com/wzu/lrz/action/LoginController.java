package com.wzu.lrz.action;

import com.wzu.lrz.Service.AsyncService;
import com.wzu.lrz.dao.UserMapper;
import com.wzu.lrz.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    AsyncService asyncService;

    //登录成功页面
    @RequestMapping("/welcome")
    public String welcome(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        String username = (String)subject.getPrincipal();

        User user=userMapper.getUserByCode(username);
        session.setAttribute("userinfo",user);
        asyncService.asynSendEmail();
        return "welcome";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    //登录请求
    @RequestMapping("/userlogin")
    public String userLogin(User user, boolean rememberMe)
    {
        Subject subject = SecurityUtils.getSubject();
        System.out.println(user.getUserCode() + "--"+user.getUserPassword());
        UsernamePasswordToken auth = new UsernamePasswordToken(user.getUserCode(), user.getUserPassword(),rememberMe);

        try {
            subject.login(auth);
            return "redirect:/welcome";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/login";
        }
    }
    //退出登录
    @RequestMapping(value = {"/loginOut"})
    public String loginOut() {

        //获取用户信息
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        return "redirect:/login";
    }

}

