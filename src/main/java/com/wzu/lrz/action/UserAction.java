package com.wzu.lrz.action;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wzu.lrz.dao.UserMapper;
import com.wzu.lrz.dao.UserRoleMapper;
import com.wzu.lrz.pojo.Provider;
import com.wzu.lrz.pojo.User;
import com.wzu.lrz.pojo.UserRole;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserAction {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRoleMapper userRoleMapper;

    private Integer now_page=1,detail_id=1;

    @RequiresRoles({"ADMIN"})
    @PutMapping("/edit")
    public String editUser(User user){

        userMapper.updateUser(user);
        System.out.println("成功修改为"+user.toString());
        return "user_detail";
    }


    @RequiresRoles({"ADMIN"})
    @RequestMapping("/update/{id}")
    @ResponseBody
    public User findUserById(@PathVariable Integer id, Model model){
        System.out.println("点更新传来的ID" + id);
        User user=userMapper.getUserById(id);
        System.out.println("员工信息"+user.toString());

        return user;
    }

    @RequiresRoles({"ADMIN"})
    @RequestMapping("/delete/{ids}")
    @ResponseBody
    public String deleteUserById(@PathVariable String ids, Model model){

        System.out.println(ids);
        String[] user_ids=ids.split(">");
        for (String i:user_ids
        ) {
            userMapper.deleteById(Integer.parseInt(i));
            System.out.println("成功删除数据id"+i);
        }
        return "user";
    }

    @RequiresRoles({"ADMIN"})
    @PostMapping("/add")
    public String addUser(User user){

        user.setCreationDate(new Date());
        System.out.println("新增用户信息"+user.toString());
        userMapper.addUser(user);

        return "user";
    }

    @RequiresRoles({"ADMIN"})
    @RequestMapping("/search/{key}")
    public String getUserByCodeName(@PathVariable String key,Model model,@RequestParam(value = "pn",
            defaultValue = "1") Integer pn){
        PageHelper.startPage(pn, 5); //紧接着的查询会被分页
        now_page=pn;
        List<User> users = userMapper.getUserListByCodeName(key);
        PageInfo page = new PageInfo(users, 5);
        for (User user:users
        ) {
            user.setUserRoleName(userRoleMapper.getUserRoleById(user.getUserRole()).getRoleName());
            user.setCreatedByer(userMapper.getUserById(user.getCreatedBy()).getUserName());
            if(user.getGender()==1){
                user.setGender_s("男");
            }else if(user.getGender()==2){
                user.setGender_s("女");
            }
        }
        List<UserRole> roles=userRoleMapper.getUserRoleList();
        model.addAttribute("roles",roles);
        model.addAttribute("pageInfo", page);
        model.addAttribute("key",key);
        return "user_search";
    }

    @RequiresRoles({"ADMIN"})
    @RequestMapping("/detail/{id}")
    public String findUserDetail(@PathVariable String id, Model model){
        System.out.println("查看用户信息"+id);
        detail_id=Integer.parseInt(id);
        User user=userMapper.getUserById(detail_id);
        user.setUserRoleName(userRoleMapper.getUserRoleById(user.getUserRole()).getRoleName());
        user.setCreatedByer(userMapper.getUserById(user.getCreatedBy()).getUserName());
        if(user.getGender()==1){
            user.setGender_s("男");
        }else if(user.getGender()==2){
            user.setGender_s("女");
        }

        List<UserRole> roles=userRoleMapper.getUserRoleList();
        model.addAttribute("roles",roles);
        model.addAttribute("user",user);

        System.out.println(user.toString());
        return "user_detail";
    }

    @RequiresRoles({"ADMIN"})
    @RequestMapping("/detail_go")
    public String findDetailUpdateGone(Model model){
        User user=userMapper.getUserById(detail_id);
        user.setUserRoleName(userRoleMapper.getUserRoleById(user.getUserRole()).getRoleName());
        user.setCreatedByer(userMapper.getUserById(user.getCreatedBy()).getUserName());
        if(user.getGender()==1){
            user.setGender_s("男");
        }else if(user.getGender()==2){
            user.setGender_s("女");
        }
        List<UserRole> roles=userRoleMapper.getUserRoleList();
        model.addAttribute("roles",roles);
        model.addAttribute("user",user);
        return "user_detail";
    }



    @RequestMapping("/exit")
    public String exit(HttpSession session) {
        session.invalidate();
        return "login";
    }

    @RequiresRoles({"ADMIN"})
    @RequestMapping("/users")
    public String findAll(Model model,
                          @RequestParam(value = "pn",
                                  defaultValue = "1") Integer pn){
        PageHelper.startPage(pn, 5); //紧接着的查询会被分页
        now_page=pn;
        List<User> users = userMapper.getUserList();
        for (User user:users
             ) {
            user.setUserRoleName(userRoleMapper.getUserRoleById(user.getUserRole()).getRoleName());
            user.setCreatedByer(userMapper.getUserById(user.getCreatedBy()).getUserName());
            if(user.getGender()==1){
                user.setGender_s("男");
            }else if(user.getGender()==2){
                user.setGender_s("女");
            }
        }
        PageInfo page = new PageInfo(users, 5);
        List<UserRole> roles=userRoleMapper.getUserRoleList();
        model.addAttribute("roles",roles);
        model.addAttribute("pageInfo", page);
        return "user";
    }

    @RequiresRoles({"ADMIN"})
    @RequestMapping("/users_go")
    public String findAllGo(Model model){
        PageHelper.startPage(now_page, 5); //紧接着的查询会被分页

        List<User> users = userMapper.getUserList();
        for (User user:users
        ) {
            user.setUserRoleName(userRoleMapper.getUserRoleById(user.getUserRole()).getRoleName());
            user.setCreatedByer(userMapper.getUserById(user.getCreatedBy()).getUserName());
            if(user.getGender()==1){
                user.setGender_s("男");
            }else if(user.getGender()==2){
                user.setGender_s("女");
            }
        }
        PageInfo page = new PageInfo(users, 5);
        List<UserRole> roles=userRoleMapper.getUserRoleList();
        model.addAttribute("roles",roles);
        model.addAttribute("pageInfo", page);
        return "user";
    }


//    @RequestMapping("/login")
//    public String login(User user, HttpSession session){
//        System.out.println(user.getUserCode() + "--"+user.getUserPassword());
//
//        User u = userMapper.findUser(user);
//        if (u != null){
//            session.setAttribute("userinfo", u);
//            return "welcome";
//        }
//
//        return "login";
//    }

}
