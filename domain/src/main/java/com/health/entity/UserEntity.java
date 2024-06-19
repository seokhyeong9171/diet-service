package com.health.entity;

import com.health.type.Gender;
import com.health.type.Region;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity(name = "user_info")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UserEntity extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name")
    private String username;
    @Column(name = "nickname")
    private String nickname;

    @Column(name = "birth")
    private LocalDate birth;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "height")
    private Double height;

    @Column(name = "weight")
    private Double weight;
    @Column(name = "goal_weight")
    private Double goalWeight;
    @Column(name = "region")
    private Region region;
    @Column(name = "exer_duration")
    private Integer exerciseDuration;

    @Column(name = "demerit")
    private Integer demerit;

    @OneToMany(mappedBy = "user")
    private List<UserWeightEntity> userWeightList;

    @OneToMany(mappedBy = "user")
    private List<ExerciseRecord> exerciseRecordList;

    @OneToMany(mappedBy = "createUser")
    private List<PostEntity> postList;


}
