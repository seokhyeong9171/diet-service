package com.health.domain.entity;

import com.health.domain.form.UserWeightDomainForm;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "weight")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UserWeightEntity extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weight_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "amount")
    private Double weight;

    @Column(name = "registration_dt")
    private LocalDate weightRegDt;


    public static UserWeightEntity createByForm(UserEntity user, UserWeightDomainForm form) {
        return UserWeightEntity.builder()
            .user(user)
            .weight(form.getWeight())
            .weightRegDt(LocalDate.now())
            .build();
    }

    public void updateByForm(UserWeightDomainForm form) {
        this.weight = form.getWeight();
    }
}
