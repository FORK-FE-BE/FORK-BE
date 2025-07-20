package com.fork.app.domain.dto;

import com.fork.app.domain.entity.enumtype.MenuCategoryEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "메뉴 응답 DTO")
public class MenuResponseDto {

    @Schema(description = "메뉴 ID", example = "1")
    private Long menuId;

    @Schema(description = "메뉴 이름", example = "짬뽕")
    private String name;

    @Schema(description = "메뉴 가격", example = "9000")
    private Integer price;

    @Schema(description = "메뉴 카테고리", example = "대표메뉴")
    private MenuCategoryEnum category;

    @Schema(description = "메뉴 이미지 URL", example = "https://fork-app-assets.s3.ap-northeast-2.amazonaws.com/menus/짬뽕.jpg")
    private String imgUrl;
}