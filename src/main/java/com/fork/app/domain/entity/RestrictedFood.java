package com.fork.app.domain.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restricted_food")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestrictedFood {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
