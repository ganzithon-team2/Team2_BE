package com.ganzi.backend.animal.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Sex {
    MALE("M", "수컷"),
    FEMALE("F", "암컷"),
    UNKNOWN("Q", "미상");

    private final String code;
    private final String description;

    public static Sex fromCode(String code) {
        for (Sex sex : values()) {
            if (sex.code.equals(code)) {
                return sex;
            }
        }
        return UNKNOWN;
    }
}
