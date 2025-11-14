package com.ganzi.backend.animal.infrastructure.mapper;

import com.ganzi.backend.animal.domain.Animal;
import org.springframework.stereotype.Component;

@Component
public class AnimalEmbeddingMapper {
    public String toEmbeddingInput(Animal animal) {
        if (animal == null) {
            return "";
        }
        return String.format(
                "품종: %s\n성별: %s\n나이: %s\n색상: %s\n체중: %s\n발견 장소: %s\n특징: %s",
                nullSafe(animal.getBreedName()),
                nullSafe(animal.getSex() != null ? animal.getSex().getDescription() : null),
                nullSafe(animal.getAge()),
                nullSafe(animal.getColor()),
                nullSafe(animal.getWeight()),
                nullSafe(animal.getFoundPlace()),
                nullSafe(animal.getSpecialMark())
        );
    }

    private String nullSafe(String value) {
        return value == null ? "" : value;
    }
}
