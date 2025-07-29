package com.fork.app.domain.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "주소 응답 DTO")
public class AddressResponseDto {
    private Long id;
    private String province;        // 시/도
    private String city;            //시/군/구
    private String roadName;        //	도로명
    private String buildingNumber;  //	건물 번호
    private String detail;          // 상세주소 (호수, 층수, 기타 등)
    private String postalCode;      //우편번호

    private int isDefault; //0 또는 1로 설정

    private String label;  // 주소 별칭 (예: 집, 회사, 부모님 집 등)


}
