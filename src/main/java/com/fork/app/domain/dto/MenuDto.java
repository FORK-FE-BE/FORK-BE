package com.fork.app.domain.dto;

import com.fork.app.domain.entity.enumtype.MenuCategoryEnum;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MenuDto {
    private Long menuId;
    private String name;
    private Integer price;
    private MenuCategoryEnum category;
    private String imgUrl;
}
