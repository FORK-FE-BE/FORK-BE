package com.fork.app.repository;

import com.fork.app.domain.entity.Menu;
import com.fork.app.domain.entity.Restaurant;
import com.fork.app.domain.entity.enumtype.RestaurantCategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("select distinct r from Restaurant r left join fetch r.menus" +
            " where r.restaurantCategoryEnum = :category")  // 카테고리를 통해서 식당과 메뉴 조인해서 식당 리스트를 가져옴
    List<Restaurant> findRestaurantsByCategory(@Param("category") RestaurantCategoryEnum category);


    @Query("select m from Menu m where m.restaurant.restaurantId = :restaurantId")
    List<Menu> findMenusByRestaurantId(Long restaurantId);

}
