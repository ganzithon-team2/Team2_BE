package com.ganzi.backend.global.oauth.client;

import com.ganzi.backend.global.code.status.ErrorStatus;
import com.ganzi.backend.global.exception.GeneralException;
import com.ganzi.backend.global.oauth.api.dto.response.OAuthTokenResponse;
import com.ganzi.backend.global.oauth.config.OAuthProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoOAuthClient implements OAuthClient {

    private static final String PROVIDER_NAME = "kakao";
    private static final String GRANT_TYPE = "authorization_code";

    private final OAuthProperties oAuthProperties;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public OAuthTokenResponse exchangeCodeForToken(String code) {
        OAuthProperties.ProviderConfig config = oAuthProperties.getProvider(PROVIDER_NAME);

        try {
            HttpHeaders headers = createHeaders();
            MultiValueMap<String, String> body = createTokenRequestBody(code, config);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

            ResponseEntity<OAuthTokenResponse> response = restTemplate.exchange(
                    config.getTokenUri(),
                    HttpMethod.POST,
                    request,
                    OAuthTokenResponse.class
            );

            if (response.getBody() == null) {
                throw new GeneralException(ErrorStatus.OAUTH_TOKEN_EXCHANGE_FAILED);
            }

            log.info("카카오 토큰 교환 성공");
            return response.getBody();

        } catch (RestClientException e) {
            log.error("카카오 토큰 교환 실패: {}", e.getMessage(), e);
            throw new GeneralException(ErrorStatus.OAUTH_TOKEN_EXCHANGE_FAILED);
        }
    }

    @Override
    public String getProviderName() {
        return PROVIDER_NAME;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    private MultiValueMap<String, String> createTokenRequestBody(
            String code,
            OAuthProperties.ProviderConfig config
    ) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", config.getClientId());
        body.add("client_secret", config.getClientSecret());
        body.add("code", code);
        return body;
    }
}
