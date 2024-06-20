package com.health.type;

import lombok.Getter;

@Getter
public enum ConsumeAmount {

    QUARTER(0.25),
    HALF(0.5),
    THREE_QUARTERS(0.75),
    ONE(1.0);


    private final Double value;

    ConsumeAmount(Double value) {
        this.value = value;
    }
}
