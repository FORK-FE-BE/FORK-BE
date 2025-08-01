package com.fork.app.domain.entity;
import com.fork.app.domain.dto.response.UserInfoResponseDto;
import com.fork.app.domain.entity.enumtype.MemberRole;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Column(nullable = false)
    private LocalDateTime createdDate; //가입일자

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses= new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RestrictedFood> restrictedFoods = new ArrayList<>();


    public void changeNickname(String nickname){
        this.name = nickname;
    }

    public void addAddress(Address address) {
        address.setUser(this);
        this.getAddresses().add(address);
    }

    public UserInfoResponseDto entityToDto() {
        return UserInfoResponseDto.builder()
                .userId(this.getUserId())
                .name(this.getName())
                .role(this.getRole())
                .email(this.getEmail())
                .createdDate(this.getCreatedDate())
                .build();
    }
//    @Column(nullable = false, length = 11)
//    private String phone;


}
