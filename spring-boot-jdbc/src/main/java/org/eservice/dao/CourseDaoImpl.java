package org.eservice.dao;

import org.eservice.entity.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CourseDaoImpl implements Dao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseDaoImpl.class);

    private JdbcTemplate jdbcTemplate;

    public CourseDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Course> rowMapper = ((resultSet, rowNum) -> {
        Course course = new Course();
        course.setCourseId(resultSet.getInt("course_id"));
        course.setTitle(resultSet.getString("title"));
        course.setDescription(resultSet.getString("description"));
        course.setLink(resultSet.getString("link"));

        return course;
    });


    @Override
    public List<Course> getAll() {
        String sql = "SELECT `course_id`,`title`, `description`, `link` FROM `course`";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Optional<Course> getById(int id) {
        String sql = "SELECT `course_id`,`title`, `description`, `link` FROM `course` WHERE `course_id` =?";

        Course courseFound = null;

        try {
            courseFound = jdbcTemplate.queryForObject(sql, new Object[]{id},rowMapper);
        } catch (DataAccessException exception) {
            LOGGER.info(String.format("No such id -> %s", id));

        }
        return Optional.of(courseFound);

    }

    @Override
    public Course save(Course course) {
        String sql = "INSERT INTO `course`(`title`,`description`, `link`) VALUES(?,?,?)";
        int insert = jdbcTemplate.update(sql, course.getTitle(), course.getDescription(), course.getLink());

        LOGGER.info("course created: ");

        return course;
    }


    @Override
    public Course updateById(int id, Course course) {

        String sql = "UPDATE `course` SET `title`=?,`description`=?,`link`=? WHERE `course_id`=?";
        int insert = jdbcTemplate.update(sql, course.getTitle(), course.getDescription(), course.getLink(),id);
        LOGGER.info("course updated: ");
        return course;
    }

    @Override
    public String deleteById(int id) {
        String sql = "DELETE FROM `course` WHERE `course_id` = ?";

       int insert = jdbcTemplate.update(sql,id);

       LOGGER.info("deleted course with id : " +id);
        return "course has been deleted ";
    }

    @Override
    public List<Course> findByTitle(String title){
        String sql = "SELECT * FROM course WHERE title = ?";

        return jdbcTemplate.query(sql,new Object[]{title}, rowMapper);

    }

    @Override
    public List<Course> findByTitleAndLink(String title, String link){
        String sql = "SELECT * FROM course WHERE title = ? AND link = ?";

        return jdbcTemplate.query(sql,new Object[]{title,link},rowMapper);

    }


}
