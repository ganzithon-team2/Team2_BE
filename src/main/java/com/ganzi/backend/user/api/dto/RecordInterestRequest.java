package com.ganzi.backend.user.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class RecordInterestRequest {

    private String desertionNo;
    private Integer dwellTimeSeconds;
    private boolean liked;
}
