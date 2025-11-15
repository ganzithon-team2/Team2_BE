package com.ganzi.backend.animal.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpstageEmbeddingResponse {
    private List<EmbeddingData> data;
    private String model;

    public List<float[]> toVectors() {
        List<float[]> out = new ArrayList<>();
        if (data == null) return out;
        for (EmbeddingData entry : data) {
            if (entry.getEmbedding() == null) continue;
            List<Float> list = entry.getEmbedding();
            float[] vec = new float[list.size()];
            for (int i = 0; i < list.size(); i++) {
                vec[i] = list.get(i);
            }
            out.add(vec);
        }
        return out;
    }
}
