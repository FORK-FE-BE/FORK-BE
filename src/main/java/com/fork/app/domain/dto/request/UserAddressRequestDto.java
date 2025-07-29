package com.fork.app.domain.dto.request;

import com.fork.app.domain.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressRequestDto {
    private String province;
    private String city;
    private String roadName;
    private String buildingNumber;
    private String detail;
    private String postalCode;
    private String label;
    public Address dtoToEntity(){
        Address address = Address.builder()

                .province(this.province)
                .city(this.city)
                .roadName(this.roadName)
                .buildingNumber(this.buildingNumber)
                .detail(this.detail)
                .postalCode(this.postalCode)
                .label(this.label)
                .build();
        return address;
    }
}
