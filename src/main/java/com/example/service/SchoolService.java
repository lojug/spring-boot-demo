package com.example.service;

import com.example.domain.Course;
import com.example.domain.Student;
import com.example.repository.CourseRepository;
import com.example.repository.StudentRepository;
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
import java.util.Optional;

@Service
@Transactional
public class SchoolService {

    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private UserDetailsManager userDetailsManager;

    @Autowired
    public SchoolService(StudentRepository studentRepository,
                         CourseRepository courseRepository,
                         UserDetailsManager userDetailsManager) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.userDetailsManager = userDetailsManager;
    }

    public void saveStudent(Student student, Optional<String> password) {
        if (student.getId() == null) {
            studentRepository.save(student);

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
            UserDetails user = new User(student.getName(), password.get(), authorities);
            userDetailsManager.createUser(user);
        } else {
            Student s = studentRepository.findOne(student.getId());
            s.setAddress(student.getAddress());
            s.setEmail(student.getEmail());
            studentRepository.save(s);
        }
    }

    public void changeStudentPassword(User user, String password) {
        userDetailsManager.changePassword(user.getPassword(), password);
    }

    public Student getStudent(Long id) {
        return studentRepository.findOne(id);
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findOne(id);
        userDetailsManager.deleteUser(student.getName());
        studentRepository.delete(id);
    }

    public Student findStudent(User user) {
        return studentRepository.findByName(user.getUsername());
    }

    public void enrollInCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findOne(studentId);
        Course course = courseRepository.findOne(courseId);
        student.getCourses().add(course);
        studentRepository.save(student);
    }

    public void dropCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findOne(studentId);
        Course course = courseRepository.findOne(courseId);
        student.getCourses().remove(course);
        studentRepository.save(student);
    }

    public Iterable<Course> getCourses() {
        return courseRepository.findAll();
    }

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    public Course getCourse(Long id) {
        return courseRepository.findOne(id);
    }

    public void deleteCourse(Long id) {
        courseRepository.delete(id);
    }

    public Iterable<Student> getStudents() {
        return studentRepository.findAll();
    }


}
