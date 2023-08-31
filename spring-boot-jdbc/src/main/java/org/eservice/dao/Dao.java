package org.eservice.dao;

import org.eservice.entity.Course;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface Dao<T> {

    public List<T> getAll();

    public Optional<T> getById(int id);

    public Course save(Course t);

    public Course updateById(int id, Course course);

    public String deleteById(int id);

    public List<T> findByTitle(String title);

    public List<Course> findByTitleAndLink(String title, String link);
}
