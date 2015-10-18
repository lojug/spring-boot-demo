package com.example.service;

import com.example.domain.Course;
import com.example.domain.Teacher;
import com.example.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StaffingService {

    private TeacherRepository teacherRepository;
    private UserDetailsManager userDetailsManager;

    @Autowired
    public StaffingService(TeacherRepository teacherRepository,
                           UserDetailsManager userDetailsManager) {
        this.teacherRepository = teacherRepository;
        this.userDetailsManager = userDetailsManager;
    }

    public void addTeacher(Teacher teacher) {
        teacherRepository.save(teacher);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_STAFF"));
        UserDetails user = new User(teacher.getName(), "password", authorities);
        userDetailsManager.createUser(user);
    }

    public void addTeacherToCourse(Teacher teacher, Course course) {
        teacher.getCourses().add(course);
        teacherRepository.save(teacher);
    }

}
