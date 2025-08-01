package com.fork.app.domain.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class AllergyRequestDto {
    private List<String> allergy;
}
