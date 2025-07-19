package com.fork.app.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue
    private Long id;

    private String city;         // 서울특별시
    private String district;     // 마포구
    private String detail;       // 와우산로 94

    private boolean isDefault;   // 기본 배송지 여부

//    private Double latitude;     // 위도 (선택)
//    private Double longitude;    // 경도 (선택)

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private LocalDateTime createdAt = LocalDateTime.now();
}
