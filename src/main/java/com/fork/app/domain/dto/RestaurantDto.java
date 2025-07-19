package com.fork.app.domain.dto;

import com.fork.app.domain.entity.Menu;
import com.fork.app.domain.entity.enumtype.RestaurantCategoryEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Builder
@Data
public class RestaurantDto {
    private Long restaurantId;
    private String name;
    private RestaurantCategoryEnum restaurantCategoryEnum;
    private String address;
    private List<MenuDto> menus;
    private List<String> storePictureUrl;
    private String phone;
    private BigDecimal rating;
    private Integer reviewCount;
    private LocalDateTime createdDate;
//    private String content;
//    private Integer minDeliveryPrice;
//    private Integer deliveryTip;
//    private Integer minDeliveryTime;
//    private Integer maxDeliveryTime;

}
