package com.ganzi.backend.rag.api.dto;

public record RagResponse (

    // LLM이 생성한 최종 답변
    String answer

   //RAG 사용 x (의미 없음)
   //List<SourceDocument> sources;
){}
