package com.fork.app.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private MenuOptionGroup optionGroup;

    @Column(nullable = false)
    private String name; // 예: "곱빼기", "치즈 추가"

    @Column(nullable = false)
    private int price; // 추가 금액 (없으면 0)
}

