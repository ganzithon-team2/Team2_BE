package com.ganzi.backend.global.oauth.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "OAuth 콜백 요청")
public record OAuthCallbackRequest(
        @NotBlank(message = "Authorization code는 필수입니다")
        @Schema(description = "OAuth 제공자로부터 받은 authorization code", example = "abc123xyz456")
        String code
) {
}
