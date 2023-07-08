package com.sakura.server;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.sakura.bean.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
@Api(value = "/",description = "这是全部的 POST 请求")
public class MyPostMethod {
    // 用于装 Cookies 信息的变量
    private static Cookie cookie;

    // 模拟用户登录成功获取到 Cookies，然后再访问其它接口获取列表
    @PostMapping(value = "/login")
    @ApiOperation(value = "登录接口，成功后获取 Cookies 信息",httpMethod = "POST")
    public String login(HttpServletResponse response, @RequestParam(value = "userName", required = true) String userName, @RequestParam(value = "password", required = true) String password){
        if(userName.equals("jack") && password.equals("123456")){
            cookie = new Cookie("login", "true");
            response.addCookie(cookie);
            return "恭喜你登录成功了！";
        }
        return "用户名或密码错误！";
    }

    @PostMapping(value = "/getMyUserList")
    @ApiOperation(value = "获取用户列表",httpMethod = "POST")
    public String getMyUserList(HttpServletRequest request, @RequestBody User u){
        User user;
        // 获取 cookies
        Cookie[] cookies = request.getCookies();
        if(Objects.isNull(cookies) || Objects.isNull(u)){
            return "参数不合法";
        }
        // 验证 cookies 是否合法
        for(Cookie c : cookies){
            if(c.getName().equals("login")
                    && c.getValue().equals("true")
                    && u.getUserName().equals("jack")
                    && u.getPassword().equals("123456")
                    ){
                user = new User();
                user.setName("rose");
                user.setAge("18");
                user.setSex("male");
                return user.toString();
            }
        }
        return "参数不合法";
    }
}


