package com.example.controller;

import com.example.domain.Student;
import com.example.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
public class StudentController {

    @Autowired
    private SchoolService schoolService;

    @RequestMapping(value = "/student/edit/{id}", method = RequestMethod.GET)
    public String editStudent(@PathVariable Long id, Model model, @RequestParam Optional<String> success) {
        Student student = schoolService.getStudent(id);
        model.addAttribute("student", student);
        model.addAttribute("courses", schoolService.getCourses());
        if (success.isPresent()) {
            model.addAttribute("success", true);
        }
        return "student";
    }

    @RequestMapping(value = "/student/save", method = RequestMethod.POST)
    public String saveStudent(@ModelAttribute Student student, @RequestParam Optional<String> password) {
        schoolService.saveStudent(student, password);
        return "redirect:/student/edit/"+student.getId()+"?success=true";
    }

    @RequestMapping(value = "/student/{studentId}/enroll/{courseId}", method = RequestMethod.GET)
    public String enrollInCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        schoolService.enrollInCourse(studentId, courseId);
        return "redirect:/student/edit/"+studentId;
    }

    @RequestMapping(value = "/student/{studentId}/drop/{courseId}", method = RequestMethod.GET)
    public String dropCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        schoolService.dropCourse(studentId, courseId);
        return "redirect:/student/edit/"+studentId;
    }

    @RequestMapping(value = "/student/password", method = RequestMethod.POST)
    public String changePassword(@AuthenticationPrincipal User user, @RequestParam String password) {
        schoolService.changeStudentPassword(user, password);
        return "redirect:/?success=true";
    }
}
