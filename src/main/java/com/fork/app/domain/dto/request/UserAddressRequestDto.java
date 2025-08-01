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

                .province(province)
                .city(city)
                .roadName(roadName)
                .buildingNumber(buildingNumber)
                .detail(detail)
                .postalCode(postalCode)
                .label(label)
                .build();
        return address;
    }
}
