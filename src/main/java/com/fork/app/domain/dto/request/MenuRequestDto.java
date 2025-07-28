package com.fork.app.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Schema(description = "메뉴 추가 DTO")
public class MenuRequestDto {
    private String name;
    private int price;
    private String menuCategory;

    @JsonIgnore // multipart/form-data에서만 받기
    private MultipartFile imageFile;

    private String imgUrl; // S3 업로드 후 설정됨
}