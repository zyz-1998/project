package com.jpaThymeleaf.service;

import com.jpaThymeleaf.entity.Student;

import java.util.List;

public interface StudentService {

    public List<Student> getUserList();

    public Student findUserById(long id);

    public void save(Student stu);

    public void edit(Student stu);

    public void delete(long id);

    boolean findByNameAndPassword(String name, String password);
}
