package com.example.controller;

import com.example.domain.Course;
import com.example.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class SchoolAdminController {

    @Autowired
    private SchoolService schoolService;

    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public String getCourses(Model model) {
        model.addAttribute("courses", schoolService.getCourses());
        return "courses";
    }

    @RequestMapping(value = "/course/save", method = RequestMethod.POST)
    public String addCourse(@ModelAttribute Course course) {
        schoolService.saveCourse(course);
        return "redirect:/courses";
    }

    @RequestMapping(value = "/course/edit/{id}", method = RequestMethod.GET)
    public String editCourse(@PathVariable Long id, Model model) {
        model.addAttribute("course", schoolService.getCourse(id));
        return "course";
    }

    @RequestMapping(value = "/course/delete/{id}", method = RequestMethod.GET)
    public String deleteCourse(@PathVariable Long id) {
        schoolService.deleteCourse(id);
        return "redirect:/courses";
    }


    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public String getStudents(Model model) {
        model.addAttribute("students", schoolService.getStudents());
        return "students";
    }

    @RequestMapping(value = "/student/delete/{id}", method = RequestMethod.GET)
    public String deleteStudent(@PathVariable Long id) {
        schoolService.deleteStudent(id);
        return "redirect:/students";
    }
}