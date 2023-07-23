package com.sakura.controller;

import com.sakura.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@Api(value = "v1",description = "用户管理系统")
@RequestMapping("v1")
public class UserManager {

    @Autowired
    private SqlSessionTemplate template;

    @ApiOperation(value = "登录接口",httpMethod = "POST")
    @PostMapping(value = "/login")
    public Boolean login(HttpServletResponse response, @RequestBody User user){
        int i = template.selectOne("login",user);
        Cookie cookie = new Cookie("login","true");
        response.addCookie(cookie);
        log.info("查询到的结果是"+i);
        if(i==1){
            log.info("登录的用户是："+user.getUserName());
            return true;
        }
        return false;
    }

    @ApiOperation(value = "添加用户接口",httpMethod = "POST")
    @PostMapping(value = "/addUser")
    public boolean addUser(HttpServletRequest request, @RequestBody User user){
        Boolean x = verifyCookies(request);

        int result=0;

        if(x!=null){
            result = template.insert("addUser",user);
        }
        if(result > 0){
            log.info("添加用户的数量是："+result);
            return true;
        }
        return false;
    }

    @ApiOperation(value = "获取用户（列表）信息接口",httpMethod = "POST")
    @PostMapping(value = "/getUserInfo")
    public List<User> getUserInfo(HttpServletRequest request,@RequestBody User user){
        Boolean x = verifyCookies(request);
        if(x==true){
            List<User> users = template.selectList("getUserInfo",user);
            log.info("getUserInfo 获取到的用户数量是"+users.size());
            return users;
        }else {
            return null;
        }
    }

    @ApiOperation(value = "更新/删除用户接口",httpMethod = "POST")
    @PostMapping(value = "/updateUserInfo")
    public int updateUser(HttpServletRequest request,@RequestBody User user){
        Boolean x = verifyCookies(request);
        int i = 0;
        if(x==true){
            i = template.update("updateUserInfo",user);
        }
        log.info("更新数据的条目数为："+i);
        return i;
    }

    private Boolean verifyCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(Objects.isNull(cookies)){
            log.info("cookies为空");
            return false;
        }

        for(Cookie cookie:cookies){
            if(cookie.getName().equals("login") &&
                    cookie.getValue().equals("true")){
                log.info("cookies 验证通过");
                return true;
            }
        }
        return false;
    }
}
