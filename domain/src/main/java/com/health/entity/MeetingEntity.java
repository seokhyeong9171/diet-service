package com.health.entity;

import com.health.type.MeetingStatus;
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
import lombok.NoArgsConstructor;

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
            @AttributeOverride(name = "latitude", column = @Column(name = "area_lat")),
            @AttributeOverride(name = "longitude", column = @Column(name = "area_lon"))
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
}
