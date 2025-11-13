package com.ganzi.backend.animal.domain;

import com.ganzi.backend.global.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "animals")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Animal extends BaseEntity {

    @Id
    private String desertionNo;

    private String rfidCode;

    // ===== 동물 기본 정보 =====
    @Column(nullable = false)
    private String breedName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AnimalType animalType;

    @Column(nullable = false)
    private String age;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NeuterStatus neuterStatus;

    @Column(nullable = false)
    private String weight;

    private String color;

    // ===== 발견 정보 =====
    @Column(nullable = false)
    private String foundDate;

    private String foundPlace;

    // ===== 공고 정보 =====
    @Column(nullable = false)
    private String noticeStartDate;

    @Column(nullable = false)
    private String noticeEndDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProcessState status;

    // ===== 보호소 정보 =====
    @Column(nullable = false)
    private String shelterName;

    @Column(nullable = false)
    private String shelterTel;

    @Column(nullable = false)
    private String shelterAddress;

    @Column(nullable = false)
    private String province;

    private String city;

    // ===== 특징 정보 =====
    private String specialMark;

    // ===== 건강 정보 =====
    private String healthInfo;
    private String vaccination;
    private String healthCheck;
    private String personality;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnimalImage> images = new ArrayList<>();

    @Builder
    public Animal(String desertionNo, String rfidCode, String breedName, AnimalType animalType, String age, Sex sex,
                  NeuterStatus neuterStatus, String weight, String color, String foundDate, String foundPlace,
                  String noticeStartDate, String noticeEndDate, ProcessState status, String shelterName,
                  String shelterTel,
                  String shelterAddress, String province, String city, String specialMark, String healthInfo,
                  String vaccination, String healthCheck, String personality) {
        this.desertionNo = desertionNo;
        this.rfidCode = rfidCode;
        this.breedName = breedName;
        this.animalType = animalType;
        this.age = age;
        this.sex = sex;
        this.neuterStatus = neuterStatus;
        this.weight = weight;
        this.color = color;
        this.foundDate = foundDate;
        this.foundPlace = foundPlace;
        this.noticeStartDate = noticeStartDate;
        this.noticeEndDate = noticeEndDate;
        this.status = status;
        this.shelterName = shelterName;
        this.shelterTel = shelterTel;
        this.shelterAddress = shelterAddress;
        this.province = province;
        this.city = city;
        this.specialMark = specialMark;
        this.healthInfo = healthInfo;
        this.vaccination = vaccination;
        this.healthCheck = healthCheck;
        this.personality = personality;
    }

    public void addImage(String imageUrl, Integer order) {
        AnimalImage image = AnimalImage.builder()
                .animal(this)
                .imageUrl(imageUrl)
                .imageOrder(order)
                .build();
        this.images.add(image);
    }

    public void addImages(List<String> imageUrls) {
        for (int i = 0; i < imageUrls.size(); i++) {
            addImage(imageUrls.get(i), i + 1);
        }
    }
}
