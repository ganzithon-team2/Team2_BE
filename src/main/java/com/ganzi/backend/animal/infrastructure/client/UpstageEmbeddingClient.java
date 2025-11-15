package com.ganzi.backend.animal.infrastructure.client;

import com.ganzi.backend.animal.infrastructure.dto.UpstageEmbeddingRequest;
import com.ganzi.backend.animal.infrastructure.dto.UpstageEmbeddingResponse;
import com.ganzi.backend.global.code.status.ErrorStatus;
import com.ganzi.backend.global.exception.GeneralException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpstageEmbeddingClient {

    private static final int BATCH_SIZE = 64;

    @Value("${upstage.api.base-url}")
    private String baseUrl;

    @Value("${upstage.api.key}")
    private String apiKey;

    @Value("${upstage.embedding.endpoint:/solar/embeddings}")
    private String endpoint;

    @Value("${upstage.embedding.model:solar-embedding-1-large-passage}")
    private String model;

    private final RestTemplate restTemplate;

    public List<float[]> embedTexts(List<String> texts) {
        if (texts == null || texts.isEmpty()) return Collections.emptyList();
        List<float[]> aggregated = new ArrayList<>();
        for (int offset = 0; offset < texts.size(); offset += BATCH_SIZE) {
            List<String> batch = texts.subList(offset, Math.min(offset + BATCH_SIZE, texts.size()));
            aggregated.addAll(callOnce(batch));
        }
        return aggregated;
    }

    private List<float[]> callOnce(List<String> batch) {
        UpstageEmbeddingRequest request = new UpstageEmbeddingRequest(model, batch);
        String url = baseUrl + endpoint;
        try {
            URI uri = URI.create(url);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            HttpEntity<UpstageEmbeddingRequest> entity = new HttpEntity<>(request, headers);

            log.debug("[Upstage] 임베딩 요청 : url={}, batchSize={}, keyPrefix={}",
                    url, batch.size(),
                    apiKey != null ? apiKey.substring(0, Math.min(8, apiKey.length())) : "null");

            UpstageEmbeddingResponse response =
                    restTemplate.postForObject(uri, entity, UpstageEmbeddingResponse.class);

            if (response == null) {
                log.warn("[Upstage] Null 응답 반환, url={}", url);
                return Collections.emptyList();
            }
            return response.toVectors();
        } catch (ResourceAccessException e) {
            if (e.getCause() instanceof SocketTimeoutException) {
                log.error("Upstage API 타임아웃", e);
                throw new GeneralException(ErrorStatus.UPSTAGE_API_TIMEOUT);
            }
            log.error("Upstage API 요청 중 자원 접근 예외 발생", e);
            throw new GeneralException(ErrorStatus.UPSTAGE_API_CALL_FAILED);
        } catch (RestClientException e) {
            log.error("Upstage API 호출 실패", e);
            throw new GeneralException(ErrorStatus.UPSTAGE_API_CALL_FAILED);
        }
    }
}
