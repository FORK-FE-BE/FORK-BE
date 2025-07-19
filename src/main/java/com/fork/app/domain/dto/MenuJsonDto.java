package com.fork.app.domain.dto;

import com.fork.app.domain.entity.enumtype.MenuCategoryEnum;
import lombok.Data;

@Data
public class MenuJsonDto {
    private String id;
    private String name;
    private Integer price;
    private MenuCategoryEnum menuCategoryEnum;
    private String imgUrl;
}
