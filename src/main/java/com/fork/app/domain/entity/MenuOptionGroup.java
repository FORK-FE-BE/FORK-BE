package com.fork.app.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuOptionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menu;

    @Column(nullable = false)
    private String name; // 예: "사이즈 선택", "토핑 추가"

    @Column(nullable = false)
    private boolean required; // 필수 선택 여부

    @OneToMany(mappedBy = "optionGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<MenuOption> options = new HashSet<>();

}

