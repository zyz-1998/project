package com.jpaThymeleaf.service.Impl;

import com.jpaThymeleaf.dao.StudentDao;
import com.jpaThymeleaf.entity.Student;
import com.jpaThymeleaf.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.sql.SQLException;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    public List<Student> getUserList() {
        return studentDao.findAll();
    }

    @Override
    public Student findUserById(long id) {
        return studentDao.findById(id);
    }

    @Override
    public void save(Student stu) {
        studentDao.save(stu);
    }

    @Override
    public void edit(Student stu) {
        studentDao.save(stu);
    }

    @Override
    public void delete(long id) {
        studentDao.deleteById(id);
    }

    @Override
    public boolean findByNameAndPassword(String name, String password) {
        Student stu = studentDao.findByNameAndPassword(name,password);

        if(stu==null){
            return false;
        }
        if(stu.getName().equals(name) && stu.getPassword().equals(password)){
            return true;
        }
        return false;
    }
}
