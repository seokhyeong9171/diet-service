package com.health.domain.entity;

import com.health.domain.form.MeetingDomainForm;
import com.health.domain.type.Region;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Embeddable
public class GeoInformation {

    @Enumerated(value = EnumType.STRING)
    private Region region;

    private String address;

    public void updateFromForm(MeetingDomainForm.MeetingArea form) {
        this.region = form.getRegion();
        this.address = form.getAddress();
    }
}
