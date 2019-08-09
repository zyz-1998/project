package com.jpaThymeleaf.dao;

import com.jpaThymeleaf.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDao extends JpaRepository<Student, Long> {

    Student findById(long id);
    Student findByNameAndPassword(String name, String password);

    void deleteById(Long id);
}
