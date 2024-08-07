package com.health.domain.entity;

import com.health.domain.form.UserDetailsDomainForm;
import com.health.domain.form.UserNicknameDomainForm;
import com.health.domain.type.Gender;
import com.health.domain.type.Region;
import com.health.domain.type.RoleType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "user_info")
@AllArgsConstructor
@NoArgsConstructor/*(access = AccessLevel.PROTECTED)*/
@Builder
public class UserEntity extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "auth_id")
    private String authId;

    @Column(name = "name")
    private String username;
    @Column(name = "nickname")
    private String nickname;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private RoleType role;

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

    @Enumerated(value = EnumType.STRING)
    @Column(name = "region")
    private Region region;

    @Column(name = "exercise_duration")
    private int exerciseDuration;

    @Column(name = "demerit")
    private int demerit;

    @OneToMany(mappedBy = "user")
    private List<UserWeightEntity> userWeightList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ExerciseRecordEntity> exerciseRecordEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "createUser")
    private List<PostEntity> postList = new ArrayList<>();


    public void updateDetails(UserDetailsDomainForm form) {
        this.height = form.getHeight();
        this.weight = form.getWeight();
        this.goalWeight = form.getGoalWeight();
        this.region = form.getRegion();
    }

    public void updateNickname(UserNicknameDomainForm form) {
        this.nickname = form.getNickname();
    }

    public void updateWeight(UserWeightEntity weightEntity) {
        this.weight = weightEntity.getWeight();
    }

    public void increaseDemerit() {
        this.demerit++;
    }

    public boolean isBlacklist() {
        return this.demerit >= 3;
    }

    public void increaseExerciseDuration() {
        this.exerciseDuration++;
    }

    public void decreaseExerciseDuration() {
        this.exerciseDuration--;
    }

    public void resetExerciseDuration() {
        this.exerciseDuration = 0;
    }

}
