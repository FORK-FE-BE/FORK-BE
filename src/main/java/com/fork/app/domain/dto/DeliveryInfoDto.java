package com.fork.app.domain.dto;


import com.fork.app.domain.entity.Address;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "배달정보 DTO")
public class DeliveryInfoDto {
    private String deliveryFee; //최소주문금액
    private Address address;
    private String deliveryTime;
    private String payment;

}
