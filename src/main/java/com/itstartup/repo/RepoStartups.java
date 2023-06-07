package com.itstartup.repo;

import com.itstartup.models.Startups;
import com.itstartup.models.enums.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepoStartups extends JpaRepository<Startups, Long> {
    List<Startups> findAllByField(Field field);

    List<Startups> findAllByNameContaining(String name);

    List<Startups> findAllByYear(int year);
}
