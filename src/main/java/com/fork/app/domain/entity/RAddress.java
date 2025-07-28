package com.fork.app.domain.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class RAddress {
    private String province;
    private String city;
    private String roadName;
    private String buildingNumber;
    private String detail;
    private String postalCode;
}
