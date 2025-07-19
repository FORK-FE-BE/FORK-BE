package com.fork.app.domain.dto;

import com.fork.app.domain.entity.Menu;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class RestaurantJsonDto {
    private String id;
    private String name;
    private String category;
    private String address;
    private List<String> storePictureUrl;
    private Map<String, List<MenuJsonDto>> menus;  // 변경된 부분
    private Double rating;
    private Integer reviewCount;
    private Boolean hasAR;
}

