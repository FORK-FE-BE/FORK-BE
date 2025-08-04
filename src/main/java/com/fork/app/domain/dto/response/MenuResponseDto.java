package com.fork.app.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
    private String category;

    @Schema(description = "메뉴 이미지 URL", example = "https://fork-app-assets.s3.ap-northeast-2.amazonaws.com/menus/짬뽕.jpg")
    private String imgUrl;

    @Schema(description = "3D 모델 파일 이름 (GLB)", example = "pizza.glb") //AR
    private String modelName;

    @Schema(description = "옵션 그룹 목록")
    private List<OptionGroupDto> optionGroups;

    @Builder
    @Data
    @Schema(description = "옵션 그룹 DTO")
    public static class OptionGroupDto {

        @Schema(description = "옵션 그룹 이름", example = "추가 토핑")
        private String name;

        @Schema(description = "필수 여부", example = "false")
        private boolean required;

        @Schema(description = "옵션 목록")
        private List<OptionDto> options;
    }

    @Builder
    @Data
    @Schema(description = "옵션 DTO")
    public static class OptionDto {

        @Schema(description = "옵션 이름", example = "치즈 추가")
        private String name;

        @Schema(description = "옵션 추가 금액", example = "1000")
        private int price;
    }
}