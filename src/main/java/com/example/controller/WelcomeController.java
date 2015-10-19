package com.example.controller;

import com.example.domain.Student;
import com.example.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class WelcomeController {

    @Autowired
    private SchoolService schoolService;

    @RequestMapping("/")
    public String welcome(@AuthenticationPrincipal User user, @RequestParam Optional<String> success, Model model) {
        String authority = user.getAuthorities().iterator().next().getAuthority();
        if ("ROLE_STUDENT".equals(authority)) {
            Student student = schoolService.findStudent(user);
            model.addAttribute("student", student);
        }
        if (success.isPresent()) {
            model.addAttribute("success", true);
        }
        return "welcome";
    }
}
