package com.ganzi.backend.animal.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AnimalType {
    DOG("417000", "개"),
    CAT("422400", "고양이"),
    ETC("429900", "기타");

    private final String code;
    private final String description;

    public static AnimalType fromCode(String code) {
        for (AnimalType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return ETC;
    }
}
