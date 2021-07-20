package com.wzu.lrz.Shiro;


import com.wzu.lrz.dao.UserMapper;
import com.wzu.lrz.pojo.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ShiroRealm extends AuthorizingRealm {


    @Autowired
    private SessionDAO sessionDAO;

    @Autowired
    private UserMapper userMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection auth) {
        //授权
        String username = (String)auth.getPrimaryPrincipal();
        System.out.println("进入到授权Realm中："+username);


        List<String> roleList=new ArrayList<String>();
        List<String> permissionList=new ArrayList<String>();

        int role_level=userMapper.getUserByCode(username).getUserRole();

        if (role_level==1) {
            roleList.add("ADMIN");
            roleList.add("EMP");
            permissionList.add("ADMIN:EMP:INDEX");
            permissionList.add("ADMIN:UPDATE");
            permissionList.add("ADMIN:DELETE");
            permissionList.add("ADMIN:EMP:LOOK");
            permissionList.add("ADMIN:EMP:SEARCH");
            permissionList.add("ADMIN:EMP:INSERT");
        }else if(role_level==2){
            roleList.add("EMP");
            permissionList.add("ADMIN:EMP:INDEX");
            permissionList.add("ADMIN:EMP:LOOK");
            permissionList.add("ADMIN:EMP:SEARCH");
            permissionList.add("ADMIN:EMP:INSERT");
        }




        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roleList);
        simpleAuthorizationInfo.addStringPermissions(permissionList);

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        //认证
        String username = (String)auth.getPrincipal();
        System.out.println("进入到认证Realm中："+username);

        //在认证之前判断当前登录用户，只允许一个账号登录
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions){
            String loginedUsername = String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));

            if(username.equals(loginedUsername)){
                session.setTimeout(0);
                break;
            }
        }

        //通过username在数据库中查询用户，判断密码
        User dbuser = userMapper.getUserByCode(username);

        //通过用户名在数据库中拿到，判断用户名和密码对不对
        if(dbuser!=null){
            SimpleAuthenticationInfo authInfo = new SimpleAuthenticationInfo(username, dbuser.getUserPassword(), "userRealm");
            return authInfo;
        }

        return null;
    }
}

