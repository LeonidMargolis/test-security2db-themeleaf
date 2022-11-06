package com.example.testsecurity2dbthemeleaf.controller;

import com.example.testsecurity2dbthemeleaf.entity.Employee;
import com.example.testsecurity2dbthemeleaf.repository.EmployeeRepository;
import com.example.testsecurity2dbthemeleaf.service.LogsService;
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
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    private final LogsService logsService;

    public EmployeeController(LogsService logsService) {
        this.logsService=logsService;
        }

    @GetMapping("/list")
    public ModelAndView getAllEmployee() {
        log.info("/list -> connection");
        ModelAndView mav = new ModelAndView("list-employee");
        mav.addObject("employee", employeeRepository.findAll());
        logsService.info(  getCurrentUserName()+ " " + "Пользователь зашел на страницу студентов");
        return mav;
    }

    @GetMapping("/addEmployeeForm")
    public ModelAndView addEmployeeForm(){
        ModelAndView mav = new ModelAndView("add-employee-form");
        Employee employee = new Employee();
        mav.addObject("employee", employee);
        logsService.info(  getCurrentUserName()+ " " + "Пользователь зашел в раздел добавления студентов");
        return mav;
    }


    @PostMapping("/saveEmployee")
    public  String saveEmployee(@ModelAttribute Employee employee){

        employeeRepository.save(employee);
        logsService.info(  getCurrentUserName()+ " " + "Пользователь сохранил нового студнта");
        return "redirect:/list";
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long employeeId){
        ModelAndView mav = new ModelAndView("add-employee-form");
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        Employee employee = new Employee();
        if (optionalEmployee.isPresent()){
            employee = optionalEmployee.get();
        }
        mav.addObject("employee", employee);
        return mav;
    }

    @GetMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam Long employeeId) {
        employeeRepository.deleteById(employeeId);
        logsService.info(  getCurrentUserName()+ " " + "Пользователь удалил студнта");
        return "redirect:/list";
    }
    private String getCurrentUserName(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }
}
