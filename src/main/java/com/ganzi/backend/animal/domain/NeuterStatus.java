package com.ganzi.backend.animal.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NeuterStatus {
    YES("Y", "중성화 완료"),
    NO("N", "중성화 안 함"),
    UNKNOWN("U", "미상");

    private final String code;
    private final String description;

    public static NeuterStatus fromCode(String code) {
        for (NeuterStatus status : values()) {
            if(status.code.equals(code)) {
                return status;
            }
        }
        return UNKNOWN;
    }
}
