package com.ganzi.backend.animal.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProcessState {
    PROTECTING("보호중", "보호중"),
    ADOPTED("종료(입양)", "입양완료"),
    NATURAL_DEATH("종료(자연사)", "자연사"),
    EUTHANIZED("종료(안락사)", "안락사"),
    RETURNED("종료(반환)", "반환");

    private final String apiValue;
    private final String description;

    public static ProcessState fromApiValue(String apiValue) {
        for (ProcessState state : values()) {
            if (state.apiValue.equals(apiValue)) {
                return state;
            }
        }
        return PROTECTING;
    }
}
