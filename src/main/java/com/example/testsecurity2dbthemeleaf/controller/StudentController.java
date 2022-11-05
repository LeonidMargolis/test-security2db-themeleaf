package com.example.testsecurity2dbthemeleaf.controller;

import com.example.testsecurity2dbthemeleaf.entity.Student;
import com.example.testsecurity2dbthemeleaf.repository.StudentRepository;
import com.example.testsecurity2dbthemeleaf.repository.RepositoryLogs;
import com.example.testsecurity2dbthemeleaf.service.LogsService;
import com.example.testsecurity2dbthemeleaf.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Slf4j
@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;
    private final LogsService logsService;

    public  StudentController(LogsService logsService) {
        this.logsService=logsService;
        }

    @GetMapping("/list")
    public ModelAndView getAllStudents() {
        log.info("/list -> connection");
        ModelAndView mav = new ModelAndView("list-students");
        mav.addObject("students", studentRepository.findAll());
        logsService.info(  getCurrentUserName()+ " " + "Пользователь зашел на страницу студентов");
        return mav;
    }

    @GetMapping("/addStudentForm")
    public ModelAndView addStudentForm(){
        ModelAndView mav = new ModelAndView("add-student-form");
        Student student = new Student();
        mav.addObject("student", student);
        logsService.info(  getCurrentUserName()+ " " + "Пользователь зашел в раздел добавления студентов");
        return mav;
    }


    @PostMapping("/saveStudent")
    public  String saveStudent(@ModelAttribute Student student){

        studentRepository.save(student);
        logsService.info(  getCurrentUserName()+ " " + "Пользователь сохранил нового студнта");
        return "redirect:/list";
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long studentId){
        ModelAndView mav = new ModelAndView("add-student-form");
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Student student = new Student();
        if (optionalStudent.isPresent()){
            student = optionalStudent.get();
        }
        mav.addObject("student", student);
        return mav;
    }

    @GetMapping("/deleteStudent")
    public String deleteStudent(@RequestParam Long studentId) {
        studentRepository.deleteById(studentId);
        logsService.info(  getCurrentUserName()+ " " + "Пользователь удалил студнта");
        return "redirect:/list";
    }
    private String getCurrentUserName(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }
}
