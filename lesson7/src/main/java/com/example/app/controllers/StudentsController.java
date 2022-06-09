package com.example.app.controllers;

import com.example.app.converters.StudentsConverter;
import com.example.app.dto.StudentDto;
import com.example.app.services.StudentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class StudentsController {
    private final StudentsService studentsService;

    @GetMapping
    public String getStudents(Model model){

        List<StudentDto> studentDtoList = studentsService.findAll().stream()
                .map(StudentsConverter::entityToDto)
                .collect(Collectors.toList());
        model.addAttribute("students", studentDtoList);
        model.addAttribute("studentDto", new StudentDto());
        return "students";
    }

    @PostMapping("/remove/{id}")
    public RedirectView remove(@PathVariable Long id) {
        final RedirectView redirectView = new RedirectView("../", true);
        studentsService.remove(id);
        return redirectView;
    }

    @PostMapping("/add")
    public RedirectView add(@ModelAttribute("studentDto") StudentDto studentDto) {
        final RedirectView redirectView = new RedirectView("/", true);
        studentsService.save(StudentsConverter.dtoToEntity(studentDto));
        return redirectView;
    }

    @PostMapping("/update")
    public RedirectView get(@ModelAttribute("studentDto") StudentDto studentDto){
        final RedirectView redirectView = new RedirectView("/", true);
        studentsService.save(StudentsConverter.dtoToEntity(studentDto));
        return redirectView;
    }
}
