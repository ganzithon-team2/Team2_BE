package com.ganzi.backend.animal.domain;

import com.ganzi.backend.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "animal_images")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnimalImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "desertion_no")
    private Animal animal;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Integer imageOrder;

    @Builder
    public AnimalImage(Animal animal, String imageUrl, Integer imageOrder) {
        this.animal = animal;
        this.imageUrl = imageUrl;
        this.imageOrder = imageOrder;
    }
}
