package org.eservice;


import org.eservice.dao.CourseDaoImpl;
import org.eservice.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class SpringBootJdbcApp {

    private static CourseDaoImpl courseDao;

    public SpringBootJdbcApp(CourseDaoImpl courseDao) {
        this.courseDao = courseDao;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJdbcApp.class);


        // Get By id

        System.out.println("\n course found: \n");
        Optional<Course> courseFound = courseDao.getById(2);
        System.out.println(courseFound);

//        // save new entry
//
        System.out.println("\n new course saved \n");
        Course course = new Course("test", "test", "test.com");
        System.out.println(course);
//
//        // update by id

        System.out.println("\n updated course \n");
        Course updatedCourse = new Course("course a", "course description", "www.course.com");

        courseDao.updateById(7, updatedCourse);
        System.out.println(updatedCourse);

        // delete by id

        System.out.println("\n deleted course \n");
        courseDao.deleteById(6);


        // Get All Course
        System.out.println("\n all courses: \n");
        List<Course> courses = courseDao.getAll();

        courses.forEach(System.out::println);


        //findByTitle
        System.out.println("\n getByTitle");
        List<Course> courseList = courseDao.findByTitle("The Complete Apache Groovy Developer Course");

        courseList.forEach(System.out::println);

        //find by title and Course

        System.out.println("\n get by title and link");
        List<Course> courseList1 = courseDao.findByTitleAndLink(
                "The Complete Apache Groovy Developer Course",
                "Everything you need to know to get started with the Groovy Programming Language"
        );
        courseList1.forEach(System.out::println);


    }

}