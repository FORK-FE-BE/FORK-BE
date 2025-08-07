package com.fork.app.domain.entity;

import com.fork.app.domain.dto.response.MenuResponseDto;
import com.fork.app.domain.dto.response.RestaurantDetailResponseDto;
import com.fork.app.domain.dto.response.RestaurantListResponseDto;
import com.fork.app.domain.entity.enumtype.RestaurantCategoryEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private RAddress address; //식당은 하나의 주소만 가짐

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MenuCategory> menuCategories = new ArrayList<>();

//    // 이 식당이 가진 메뉴들
//    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
//    @Builder.Default
//    private List<Menu> menus = new ArrayList<>();

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

    @Column(length = 255)
    private String introText;  // 한줄 소개

    @Column(length = 20)
    private String paymentMethod;  // 예: "앱 결제", "만나서 결제"

    @Column(nullable = false)
    private LocalDateTime createdDate;
    private boolean hasAR;
    private boolean hasCoupon;

    public RestaurantDetailResponseDto toDetailDto(Map<String, List<MenuResponseDto>> categorizedMenus) {
        String deliveryInfo = String.format("최소주문 %,d원 / 배달팁 %,d원", this.minDeliveryPrice, this.deliveryTip);

        return RestaurantDetailResponseDto.builder()
                .id(this.restaurantId)
                .name(this.name)
                .rating(this.rating)
                .reviewCount(this.reviewCount)
                .deliveryInfo(deliveryInfo)
                .storePictureUrl(this.storePictureUrl)
                .menus(categorizedMenus)
                .minDeliveryPrice(this.minDeliveryPrice)
                .minDeliveryTime(this.minDeliveryTime)
                .maxDeliveryTime(this.maxDeliveryTime)
                .province(this.address.getProvince())
                .city(this.address.getCity())
                .roadName(this.address.getRoadName())
                .buildingNumber(this.address.getBuildingNumber())
                .introText(this.introText)
                .paymentMethod(this.paymentMethod)
                .build();
    }

    public RestaurantListResponseDto toListDto(List<Menu> menus) {
        List<MenuResponseDto> menuDtos = menus.stream()
                .map(Menu::entityToDto)
                .collect(Collectors.toList());

        return RestaurantListResponseDto.builder()
                .id(this.restaurantId)
                .name(this.name)
                .menus(menuDtos)  // ✅ 여기에 담아줌
                .rating(this.rating)
                .reviewCount(this.reviewCount)
                .hasAR(this.hasAR)
                .hasCoupon(this.hasCoupon)
                .build();
    }
}
