package com.health.domain.entity;

import com.health.domain.form.ExerciseRecordDomainForm;
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
@Entity(name = "exercise_record")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ExerciseRecordEntity extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exer_rec_id")
    private Long id;

    @Column(name = "date")
    private LocalDate exerciseRecDt;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;


    public static ExerciseRecordEntity createRecord
        (UserEntity user, ExerciseRecordDomainForm form) {

        return ExerciseRecordEntity.builder()
            .exerciseRecDt(LocalDate.now())
            .description(form.getDescription())
            .user(user)
            .build();
    }

    public void updateRecord(ExerciseRecordDomainForm form) {
        this.description = form.getDescription();
    }
}
