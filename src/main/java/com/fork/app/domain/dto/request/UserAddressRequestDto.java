package com.fork.app.domain.dto.request;


import com.fork.app.domain.entity.Address;
import com.fork.app.domain.entity.User;
import lombok.Data;

@Data
public class UserAddressRequestDto {
    private String province;
    private String city;
    private String roadName;
    private String buildingNumber;
    private String detail;
    private String postalCode;

    public Address dtoToEntity(){
        Address address = Address.builder()
                .province(this.getProvince())
                .city(this.getCity())
                .roadName(this.getRoadName())
                .buildingNumber(this.getBuildingNumber())
                .detail(this.getDetail())
                .postalCode(this.getPostalCode())
                .build();
        return address;
    }
}
