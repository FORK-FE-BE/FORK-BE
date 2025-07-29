package com.fork.app.domain.entity;

import com.fork.app.domain.dto.response.AddressResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id @GeneratedValue
    private Long id;

    private String province;        // 시/도
    private String city;            //시/군/구
    private String roadName;        //	도로명
    private String buildingNumber;  //	건물 번호
    private String detail;          // 상세주소 (호수, 층수, 기타 등)
    private String postalCode;      //우편번호

    private int isDefault; //0 또는 1로 설정

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    private String label;  // 주소 별칭 (예: 집, 회사, 부모님 집 등)

    public AddressResponseDto entityToDto(){
        return AddressResponseDto.builder()
                .id(this.id)
                .province(this.province)
                .city(this.city)
                .roadName(this.roadName)
                .buildingNumber(this.buildingNumber)
                .detail(this.detail)
                .postalCode(this.postalCode)
                .isDefault(this.getIsDefault())
                .label(this.label)
                .build();

    }
}
