package com.example.app.services;

import com.example.app.entities.Student;
import com.example.app.repositories.StudentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentsService {
    private final StudentsRepository studentsRepository;

    public List<Student> findAll() {
        return studentsRepository.findAll();
    }

    public void remove(Long id) {
        studentsRepository.deleteById(id);
    }

    public Optional<Student> get(Long id){
        return studentsRepository.findById(id);
    }

    public void save(Student student) {
        studentsRepository.save(student);
    }
}
