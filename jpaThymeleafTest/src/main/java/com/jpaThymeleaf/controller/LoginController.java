package com.jpaThymeleaf.controller;

import com.jpaThymeleaf.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    StudentService studentService1;

    @PostMapping(value = "/userLogin")
    public String login(@RequestParam("username")String username,
                        @RequestParam("password")String password,
                        Map<String,Object> map,
                        HttpSession session){
        if(studentService1.findByNameAndPassword(username,password)){
            session.setAttribute("loginUser",username);
            return "redirect:/list";
        }
        else{
            map.put("loginMsg","账户密码错误请重新输入");
            return "/user/login";
        }
    }
}
