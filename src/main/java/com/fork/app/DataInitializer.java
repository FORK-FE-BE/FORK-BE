package com.fork.app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fork.app.domain.dto.MenuJsonDto;
import com.fork.app.domain.dto.RestaurantJsonDto;
import com.fork.app.domain.entity.Menu;
import com.fork.app.domain.entity.Restaurant;
import com.fork.app.domain.entity.enumtype.MenuCategoryEnum;
import com.fork.app.domain.entity.enumtype.RestaurantCategoryEnum;
import com.fork.app.repository.MenuRepository;
import com.fork.app.repository.RestaurantRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component // 스프링 컨테이너가 DataInitializer를 빈으로 생성
@RequiredArgsConstructor // final 필드를 자동 생성자 주입
public class DataInitializer {

    private final RestaurantRepository restaurantRepository; // Restaurant 엔티티 DB 작업용 레포지토리
    private final MenuRepository menuRepository; // Menu 엔티티 DB 작업용 레포지토리
    private final ObjectMapper objectMapper; // JSON 파싱을 위한 Jackson ObjectMapper

    @PostConstruct // 빈 초기화 후 자동으로 실행되는 메서드 지정
    @Transactional // 메서드 내 DB 작업을 트랜잭션 범위로 감싸줌 (원자성 보장)
    public void init() throws IOException {
        // resources 폴더 내 data/restaurantsByCategory.json 파일을 읽기 위한 리소스 객체 생성
        ClassPathResource resource = new ClassPathResource("data/restaurantsByCategory.json");

        // JSON 파일을 Map<String, List<RestaurantJsonDto>> 타입으로 파싱
        // key: 음식 카테고리명 (ex. 중식), value: 해당 카테고리의 식당 리스트
        Map<String, List<RestaurantJsonDto>> data = objectMapper.readValue(
                resource.getInputStream(),
                new TypeReference<Map<String, List<RestaurantJsonDto>>>() {});

        // 맵을 순회하며 각 카테고리와 그에 속한 식당 리스트 처리
        for (Map.Entry<String, List<RestaurantJsonDto>> entry : data.entrySet()) {
            RestaurantCategoryEnum restaurantCategoryEnum = RestaurantCategoryEnum.valueOf(entry.getKey()); // 카테고리명
            List<RestaurantJsonDto> restaurants = entry.getValue(); // 식당 DTO 리스트

            for (RestaurantJsonDto dto : restaurants) {
                // Restaurant 엔티티 빌더로 생성
                Restaurant restaurant = Restaurant.builder()
                        .name(dto.getName()) // JSON 데이터에서 식당명 세팅
                        .restaurantCategoryEnum(restaurantCategoryEnum) // 현재 반복중인 카테고리명 세팅
                        .storePictureUrl(dto.getStorePictureUrl() != null ? dto.getStorePictureUrl() : List.of())
                        // 이하 필수 필드는 임시 기본값 지정 (실제 값으로 변경 필요)
                        .address("기본주소") // 주소 기본값
                        .phone("010-0000-0000") // 전화번호 기본값
                        .minDeliveryPrice(10000) // 최소배달가격 예시
                        .deliveryTip(3000) // 배달팁 예시
                        // 평점은 JSON에 값이 없으면 0으로 설정
                        .rating(BigDecimal.valueOf(dto.getRating() != null ? dto.getRating() : 0))
                        // 리뷰 수 역시 JSON에 없으면 0으로 설정
                        .reviewCount(dto.getReviewCount() != null ? dto.getReviewCount() : 0)
                        // 현재 시간으로 생성일 세팅
                        .createdDate(LocalDateTime.now())
                        //.modifiedDate(LocalDateTime.now()) // 필요시 활성화
                        //.status("OPEN")                    // 필요시 활성화
                        .build();

                // DB에 저장 (식당 저장)
                restaurantRepository.save(restaurant);

                // 메뉴 리스트가 null이 아니면 메뉴도 반복하여 저장
                if (dto.getMenus() != null) {
                    Map<String, List<MenuJsonDto>> menuMap = dto.getMenus();
                    for (Map.Entry<String, List<MenuJsonDto>> entry2 : menuMap.entrySet()) {
                        MenuCategoryEnum categoryEnum = MenuCategoryEnum.valueOf(entry2.getKey()); // "대표메뉴", "인기메뉴" 등
                        List<MenuJsonDto> menus = entry2.getValue();
                        for (MenuJsonDto menuDto : menus) {
                            Menu menu = Menu.builder()
                                    .restaurant(restaurant)
                                    .name(menuDto.getName())
                                    .price(menuDto.getPrice() != null ? menuDto.getPrice() : 0)
                                    .category(categoryEnum) // 메뉴 분류 필드도 세팅해야함
                                    .imgUrl(menuDto.getImgUrl())
                                    .createdDate(LocalDateTime.now())
                                    .build();
                            menuRepository.save(menu);
                        }
                    }
                }
            }
        }
    }
}
