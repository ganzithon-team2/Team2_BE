package com.ganzi.backend.global.oauth.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "OAuth 토큰 응답")
public record OAuthTokenResponse(
        @JsonProperty("access_token")
        @Schema(description = "OAuth 액세스 토큰")
        String accessToken,

        @JsonProperty("token_type")
        @Schema(description = "토큰 타입", example = "Bearer")
        String tokenType,

        @JsonProperty("refresh_token")
        @Schema(description = "OAuth 리프레시 토큰")
        String refreshToken,

        @JsonProperty("id_token")
        @Schema(description = "OpenID Connect ID 토큰 (JWT)")
        String idToken,

        @JsonProperty("expires_in")
        @Schema(description = "액세스 토큰 만료 시간 (초)")
        Integer expiresIn,

        @JsonProperty("scope")
        @Schema(description = "허용된 권한 범위")
        String scope
) {
}
