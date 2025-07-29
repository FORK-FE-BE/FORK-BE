package com.fork.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoMapService {

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public Optional<Coordinate> getCoordinateFromAddress(String roadAddress) {
        if (roadAddress == null || roadAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("주소가 비어 있습니다.");
        }

        String url = UriComponentsBuilder
                .fromHttpUrl("https://dapi.kakao.com/v2/local/search/address.json")
                .queryParam("query", roadAddress)
                .build()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            List<Map<String, Object>> documents = (List<Map<String, Object>>) response.getBody().get("documents");

            if (documents != null && !documents.isEmpty()) {
                Map<String, Object> first = documents.get(0);
                String x = (String) first.get("x");
                String y = (String) first.get("y");
                return Optional.of(new Coordinate(x, y));
            }
        }

        return Optional.empty();
    }

    public record Coordinate(String longitude, String latitude) {}
}
