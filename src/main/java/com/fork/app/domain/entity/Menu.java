package com.fork.app.domain.entity;

import com.fork.app.domain.dto.response.MenuResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private MenuCategory category;

//    @Column(nullable = false)
//    private MenuCategoryEnum category;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "model_name")
    private String modelName;

    @Column(nullable = false)
    private Integer price;

    private String imgUrl;
//    @Column(nullable = false)
    private LocalDateTime createdDate;

//    @Column(nullable = false)
    private LocalDateTime modifiedDate;

//    @Column(nullable = false, length = 255)
    private String status;

    public MenuResponseDto entityToDto() {
        return MenuResponseDto.builder()
                .menuId(this.getMenuId())
                .name(this.getName())
                .price(this.getPrice())
                .imgUrl(this.getImgUrl())
                .category(this.getCategory().getName())
                .build();
    }

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<MenuOptionGroup> optionGroups = new ArrayList<>();


    //    @Column(columnDefinition = "TEXT")
//    private String menuPictureUrl;

//    @Column(nullable = false)
//    private Boolean popularity = false;
//    @Column(nullable = false)
//    private Long ARMenuID; // 필요 시 ARMenu와 연관관계 설정 가능
}
