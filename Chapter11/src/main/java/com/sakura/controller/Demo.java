package com.sakura.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.mybatis.spring.SqlSessionTemplate;
import com.sakura.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Log4j
@RestController
@Api(value = "v1",description = "这是第一个版本的 demo")
@RequestMapping("v1")
public class Demo {

    // 首先获取一个执行 sql 语句的对象

    @Autowired
    private SqlSessionTemplate template;

    @GetMapping(value = "/getUserCount")
    @ApiOperation(value = "可以获取到的用户数",httpMethod = "GET")
    public int getUserCount(){
        // getUserCount对应 mysql.xml 中的配置
        int a = template.selectOne("getUserCount");
       return a;
    }

    @PostMapping(value = "/addUser")
    public int addUser(@RequestBody User user){
        return template.insert("addUser",user);
    }

    @PostMapping(value = "/updateUser")
    public int updateUser(@RequestBody User user){
        return template.update("updateUser",user);
    }

    @PostMapping(value = "/deleteUser")
    public int delUser(@RequestParam int id){
        return template.delete("deleteUser",id);
    }
}
