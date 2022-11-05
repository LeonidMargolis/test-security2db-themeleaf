package com.example.testsecurity2dbthemeleaf.controller;

import com.example.testsecurity2dbthemeleaf.dto.UserDto;
import com.example.testsecurity2dbthemeleaf.entity.User;
import com.example.testsecurity2dbthemeleaf.repository.RepositoryLogs;
import com.example.testsecurity2dbthemeleaf.service.LogsService;
import com.example.testsecurity2dbthemeleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class SecurityController {

    private UserService userService;

    private final LogsService logsService;
    @Autowired
    public  SecurityController(UserService userService, LogsService logsService) {
        this.logsService=logsService;
        this.userService=userService;}

    @GetMapping("/index")
    public String home() {
        logsService.info("Пользователь зашел на главную страницу");
        return "index";}

    @GetMapping("/login")
    public String login() {
        logsService.info("Пользователь зашел на страницу логин");
        return "login";}

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "На этот адрес электронной почты уже зарегестрированна учетная запись");
        }
        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }
        userService.saveUser(userDto);
        logsService.info( "Пользователь зашел на страницу регистрации");
        return "redirect:/register?success";
    }
    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users =userService.findAllUsers();
        model.addAttribute("users", users);
        logsService.info("Пользователь зашел на страницу студенов");
        return "users";
    }

}
