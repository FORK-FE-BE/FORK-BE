package com.fork.app.domain.dto.request;


import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RAddressRequestDto {
    private String province;
    private String city;
    private String roadName;
    private String buildingNumber;
    private String detail;
    private String postalCode;

    private Double latitude;
    private Double longitude;
}
