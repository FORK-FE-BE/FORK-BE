package com.fork.app.controller;

import com.fork.app.service.KakaoMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LocationController {

    private final KakaoMapService kakaoMapService;

    @GetMapping("/coordinate")
    public ResponseEntity<?> getCoord(@RequestParam String address) {
        return kakaoMapService.getCoordinateFromAddress(address)
                .map(coord -> Map.of("longitude", coord.longitude(), "latitude", coord.latitude()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().body(Map.of("error","좌표를 찾을 수 없습니다.")));
    }
}
