package com.ganzi.backend.animal.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmbeddingData {
    private String object;
    private Integer index;
    private List<Float> embedding;
}
