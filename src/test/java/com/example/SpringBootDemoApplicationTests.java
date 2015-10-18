package com.example;

import com.example.domain.Course;
import com.example.domain.Teacher;
import com.example.repository.CourseRepository;
import com.example.repository.TeacherRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootDemoApplication.class)
@WebAppConfiguration
public class SpringBootDemoApplicationTests {

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void shouldAddTeacher() {
		Teacher teacher = new Teacher();
		teacher.setName("Einstein");

		teacherRepository.save(teacher);

		assertNotNull(teacher.getId());
	}

	@Test
	public void shouldAddTeacherToCourse() {
		Course course = new Course();
		course.setCourseName("Computer Science 101");
		course.setCourseDescription("Learn the basics of programming.");

		courseRepository.save(course);

		Teacher teacher = new Teacher();
		teacher.setName("Einstein");

		teacherRepository.save(teacher);

		teacher.getCourses().add(course);

		teacherRepository.save(teacher);

		Teacher t = teacherRepository.findOne(teacher.getId());

		assertEquals(1, t.getCourses().size());


	}

}
