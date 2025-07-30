package com.fork.app.repository;

import com.fork.app.domain.entity.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    @EntityGraph(attributePaths = {
            "restaurant",
            "restaurant.address"
    })
    List<Menu> findAll();
}
