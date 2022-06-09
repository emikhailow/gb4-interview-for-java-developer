package com.example.app.dto;

import com.example.app.entities.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Component
@NoArgsConstructor
@Setter
@Getter
public class StudentDto {
    private Long id;
    private String name;
    private Integer age;

    public StudentDto(Student student) {
        this.name = student.getName();
        this.age = student.getAge();
    }
}
