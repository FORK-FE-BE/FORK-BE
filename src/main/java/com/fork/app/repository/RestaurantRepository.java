package com.fork.app.repository;

import com.fork.app.domain.entity.Menu;
import com.fork.app.domain.entity.Restaurant;
import com.fork.app.domain.entity.enumtype.RestaurantCategoryEnum;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @EntityGraph(attributePaths = {"menuCategories"})
    @Query("select r from Restaurant r where r.restaurantCategoryEnum = :category")
    List<Restaurant> findRestaurantsByCategory(@Param("category") RestaurantCategoryEnum category);

    @Query("select m from Menu m where m.category.id in :categoryIds")
    List<Menu> findMenusByCategoryIds(@Param("categoryIds") List<Long> categoryIds);

    @Query("select m from Menu m where m.restaurant.restaurantId = :restaurantId")
    List<Menu> findMenusByRestaurantId(Long restaurantId);

}
