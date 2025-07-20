package com.fork.app.domain.dto;

import com.fork.app.domain.entity.enumtype.MenuCategoryEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "더미 데이터 파일 변환 DTO")
public class MenuJsonDto {
    private String id;
    private String name;
    private Integer price;
    private MenuCategoryEnum menuCategoryEnum;
    private String imgUrl;
}
