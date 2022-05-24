package com.geekbrains.app.entities;

import java.util.List;

public interface StudentDao {
    Student findById(Long id);
    List<Student> findAll();
    void save(Student student);
    void delete(Long id);
    void deleteAll();
    void save(List<Student> students);

}
