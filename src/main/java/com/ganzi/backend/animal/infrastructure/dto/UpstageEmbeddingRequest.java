package com.ganzi.backend.animal.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record UpstageEmbeddingRequest(
        @JsonProperty("model") String model,
        @JsonProperty("input") List<String> input
) {}
