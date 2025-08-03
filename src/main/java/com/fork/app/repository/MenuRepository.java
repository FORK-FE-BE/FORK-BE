package com.fork.app.repository;

import com.fork.app.domain.entity.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    @EntityGraph(attributePaths = {
            "restaurant",
            "restaurant.address"
    })
    List<Menu> findAll();

    @Query("SELECT m FROM Menu m " +
            "LEFT JOIN FETCH m.restaurant " +
            "LEFT JOIN FETCH m.category " +
            "LEFT JOIN FETCH m.optionGroups og " +
            "WHERE m.menuId = :menuId")
    Optional<Menu> findWithOptionGroupsByMenuId(@Param("menuId") Long menuId);

//    @Query("SELECT m FROM Menu m " +
//            "LEFT JOIN FETCH m.optionGroups og " +
//            "LEFT JOIN FETCH og.options " +
//            "WHERE m.menuId = :menuId")
//    Optional<Menu> findWithOptionGroupsAndOptionsByMenuId(@Param("menuId") Long menuId);

}
