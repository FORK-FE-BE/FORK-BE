package com.fork.app.domain.entity;

import com.fork.app.domain.entity.enumtype.MenuCategoryEnum;
import com.fork.app.domain.entity.enumtype.RestaurantCategoryEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurantId", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private MenuCategoryEnum category;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private Integer price;

    private String imgUrl;
//    @Column(nullable = false)
    private LocalDateTime createdDate;

//    @Column(nullable = false)
    private LocalDateTime modifiedDate;

//    @Column(nullable = false, length = 255)
    private String status;

    //    @Column(columnDefinition = "TEXT")
//    private String menuPictureUrl;

//    @Column(nullable = false)
//    private Boolean popularity = false;
//    @Column(nullable = false)
//    private Long ARMenuID; // 필요 시 ARMenu와 연관관계 설정 가능
}
