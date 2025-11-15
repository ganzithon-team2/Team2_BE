package com.ganzi.backend.animal.api.doc;

import com.ganzi.backend.global.code.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(
        name = "유기동물 임베딩 관리",
        description = "유기동물 추천 기능을 위한 임베딩 생성·관리 API (관리자용)"
)
public interface AnimalEmbeddingAdminControllerDoc {

    @Operation(
            summary = "전체 유기동물 임베딩 일괄 생성",
            description = """
                    DB에 저장된 모든 유기동물 정보를 기반으로 Upstage Embedding API를 호출하여 임베딩을 생성합니다.
                    
                    ### 처리 내용
                    - Animal 엔티티의 주요 특성(품종, 성별, 나이, 색상, 체중, 발견 장소, 특징 등)을 텍스트로 구성
                    - Upstage 임베딩 API에 배치 단위(예: 64개 이하)로 요청
                    - 응답으로 받은 벡터를 JSON 문자열 형태로 animal_embeddings 테이블에 저장
                    - Animal과 1:1 관계(구조번호 desertionNo 기준)로 매핑
                    
                    ### 실행 시점
                    - 초기 데이터 동기화가 완료된 후, 추천 기능을 활성화하기 전에 1회 실행
                    - 추천 로직 개선 또는 임베딩 모델 변경 시 재실행
                    - 신규 동물에 대한 임베딩이 누락된 경우 재생성 용도
                    
                    ### 유의 사항
                    - 전체 동물 데이터를 순회하며 순차적으로 임베딩을 생성하므로, 데이터 양에 따라 시간이 소요될 수 있습니다.
                    - Upstage API 호출은 내부적으로 배치 단위로 나누어 진행되어, 요청당 최대 처리 개수 제한을 넘지 않도록 합니다.
                    """
    )
    ResponseEntity<ApiResponse<String>> generateAll();
}
