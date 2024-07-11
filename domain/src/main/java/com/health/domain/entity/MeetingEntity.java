package com.health.domain.entity;

import com.health.domain.form.MeetingDomainForm;
import com.health.domain.type.MeetingStatus;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "meeting")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MeetingEntity extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long id;

    @Column(name = "name")
    private String meetingName;

    @Column(name = "description")
    private String meetingDescription;

    @Column(name = "min_participant")
    private Integer minParticipant;

    @Column(name = "max_participant")
    private Integer maxParticipant;

    @Column(name = "deadline")
    private LocalDate meetingDeadLine;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "region", column = @Column(name = "area_region")),
            @AttributeOverride(name = "latitude", column = @Column(name = "area_latitude")),
            @AttributeOverride(name = "longitude", column = @Column(name = "area_longitude"))
    })
    private GeoInformation meetingArea;

    @Column(name = "date")
    private LocalDateTime meetingDt;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private MeetingStatus meetingStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity establishedUser;

    @OneToMany(mappedBy = "meeting")
    private List<MeetingParticipantEntity> participantList = new ArrayList<>();

    public static MeetingEntity createFromForm(UserEntity user, MeetingDomainForm form) {
        return MeetingEntity.builder()
            .meetingName(form.getMeetingName())
            .meetingDescription(form.getMeetingDescription())
            .minParticipant(form.getMinParticipant())
            .maxParticipant(form.getMaxParticipant())
            .meetingDeadLine(form.getMeetingDeadLine())
            .meetingArea(geoFromForm(form.getMeetingArea()))
            .meetingDt(form.getMeetingDt())
            .meetingStatus(MeetingStatus.RECRUITING)
            .establishedUser(user)
            .build();
    }

    public void updateMeeting(MeetingDomainForm form) {
        this.meetingName = form.getMeetingName();
        this.meetingDescription = form.getMeetingDescription();
        this.minParticipant = form.getMinParticipant();
        this.maxParticipant = form.getMaxParticipant();
        this.meetingDeadLine = form.getMeetingDeadLine();
        this.meetingDt = form.getMeetingDt();
        this.meetingArea.updateFromForm(form.getMeetingArea());
    }

    public void cancel() {
        this.meetingStatus = MeetingStatus.CANCELED;
    }

    private static GeoInformation geoFromForm(MeetingDomainForm.MeetingArea meetingArea) {
        return GeoInformation.builder()
            .region(meetingArea.getRegion())
            .address(meetingArea.getAddress())
            .build();
    }
}
