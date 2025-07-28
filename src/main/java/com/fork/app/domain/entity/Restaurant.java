package com.fork.app.domain.entity;

import com.fork.app.domain.entity.enumtype.RestaurantCategoryEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long restaurantId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RestaurantCategoryEnum restaurantCategoryEnum;

    @Embedded
    private RAddress address;

    // 이 식당이 가진 메뉴들
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Menu> menus = new ArrayList<>();

    //식당 대표 이미지 URL리스트를 서브 테이블로 저장(새로운 테이블 생성)
    @ElementCollection
    @CollectionTable(
            name = "restaurant_images",
            joinColumns = @JoinColumn(name = "restaurant_id") //외래키 이름
    )
    @Column(name = "image_url")
    @Builder.Default
    private List<String> storePictureUrl = new ArrayList<>();

    private String phone;

    @Column(length = 255)
    private String content;

    @Column(nullable = false)
    private Integer minDeliveryPrice;

    @Column(nullable = false)
    private Integer deliveryTip;

    private Integer minDeliveryTime;

    private Integer maxDeliveryTime;

    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal rating;

    @Column(nullable = false)
    private Integer reviewCount;

    @Column(nullable = false)
    private LocalDateTime createdDate;
    private boolean hasAR;
    private boolean hasCoupon;

//    @Column(nullable = false)
//    private LocalDateTime modifiedDate;

//    @Column(nullable = false)
//    private String status;

//    @Column(length = 255)
//    private String operationHours;
}
