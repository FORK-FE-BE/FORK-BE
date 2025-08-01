package com.fork.app.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RAddress {
    private String province;
    private String city;
    private String roadName;
    private String buildingNumber;
    private String detail;
    private String postalCode;
    private Double latitude;
    private Double longitude;
}
