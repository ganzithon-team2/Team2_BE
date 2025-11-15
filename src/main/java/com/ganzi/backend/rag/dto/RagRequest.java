package com.ganzi.backend.rag.dto;

import jakarta.validation.constraints.NotBlank;

public record RagRequest (

    @NotBlank(message = "무엇이든 물어보세요.")
    String query

    // 대화의 연속성을 위한 세션 ID 또는 사용자ID 추가 고려
    // String userId;
){}
