package com.fork.app.domain.dto.response;


import com.fork.app.domain.dto.request.AllergyMenu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllergyResponseDto {
    private Long userId;
    private List<String> allergyList;
}
