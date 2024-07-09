package com.health.domain.entity;

import static com.health.domain.type.AdmissionStatus.APPROVAL;
import static com.health.domain.type.AdmissionStatus.CANCEL;
import static com.health.domain.type.AdmissionStatus.DECLINE;
import static com.health.domain.type.AdmissionStatus.PENDING;

import com.health.domain.type.AdmissionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "meeting_participant")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MeetingParticipantEntity extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_participant_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private MeetingEntity meeting;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity participant;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private AdmissionStatus admissionStatus;

    public static MeetingParticipantEntity enroll(UserEntity participant, MeetingEntity meeting) {
        return MeetingParticipantEntity.builder()
            .meeting(meeting).participant(participant).admissionStatus(PENDING)
            .build();
    }

    public void permit() {
        this.admissionStatus = APPROVAL;
    }

    public void decline() {
        this.admissionStatus = DECLINE;
    }

    public void cancel() {
        this.admissionStatus = CANCEL;
    }
}
