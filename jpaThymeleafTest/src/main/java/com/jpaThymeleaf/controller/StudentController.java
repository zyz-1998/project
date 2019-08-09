package com.jpaThymeleaf.controller;

import com.jpaThymeleaf.entity.Student;
import com.jpaThymeleaf.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class StudentController {
    @Resource
    StudentService studentService;

    @RequestMapping("/list")
    public String list(Model model) {
        List<Student> users=studentService.getUserList();
        model.addAttribute("users", users);
        return "user/list";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "user/userAdd";
    }

    @RequestMapping("/add")
    public String add(Student stu) {
        studentService.save(stu);
        return "redirect:/list";
    }

    @RequestMapping("/toEdit")
    public String toEdit(Model model,Long id) {
        Student stu=studentService.findUserById(id);
        model.addAttribute("user", stu);
        return "user/userEdit";
    }

    @RequestMapping("/edit")
    public String edit(Student stu) {
        studentService.edit(stu);
        return "redirect:/list";
    }


    @RequestMapping("/delete")
    public String delete(Long id) {
        studentService.delete(id);
        return "redirect:/list";
    }

}
