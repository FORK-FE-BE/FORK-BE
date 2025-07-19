package com.fork.app.domain.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForkPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long forkPointId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 255)
    private String content;

    @Column(nullable = false)
    private Integer deductedPrice;

    @Column(nullable = false)
    private Integer minDeliveryPrice;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private LocalDateTime expiredDate;

//    @Column(nullable = false)
//    private LocalDateTime modifiedDate;

//    @Column(nullable = false, length = 255)
//    private String status;
}
