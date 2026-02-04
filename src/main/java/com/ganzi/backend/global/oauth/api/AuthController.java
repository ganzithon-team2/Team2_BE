package com.ganzi.backend.global.oauth.api;

import com.ganzi.backend.global.code.dto.ApiResponse;
import com.ganzi.backend.global.oauth.api.doc.AuthControllerDoc;
import com.ganzi.backend.global.oauth.api.dto.request.OAuthCallbackRequest;
import com.ganzi.backend.global.oauth.api.dto.response.LoginResponse;
import com.ganzi.backend.global.oauth.api.dto.response.UserInfoResponse;
import com.ganzi.backend.global.oauth.application.AuthService;
import com.ganzi.backend.global.oauth.application.OAuthService;
import com.ganzi.backend.global.security.userdetails.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDoc {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final int BEARER_PREFIX_LENGTH = 7;

    private final OAuthService oAuthService;
    private final AuthService authService;

    @Override
    @PostMapping("/{provider}/callback")
    public ResponseEntity<ApiResponse<LoginResponse>> oauthCallback(
            @PathVariable String provider,
            @Valid @RequestBody OAuthCallbackRequest request
    ) {
        LoginResponse response = oAuthService.loginWithCode(provider, request.code());
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

    @Override
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getUserInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        UserInfoResponse response = authService.getUserInfo(userDetails.getUser().getId());
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

    @Override
    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<LoginResponse>> reissueTokens(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestHeader("RefreshToken") String refreshToken
    ) {
        String accessToken = extractToken(authorizationHeader);
        LoginResponse response = authService.reissueTokens(accessToken, refreshToken);
        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

    @Override
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestHeader("RefreshToken") String refreshToken
    ) {
        String accessToken = extractToken(authorizationHeader);
        authService.logout(accessToken, refreshToken);
        return ResponseEntity.ok(ApiResponse.onSuccess(null));
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader.startsWith(BEARER_PREFIX)) {
            return authorizationHeader.substring(BEARER_PREFIX_LENGTH);
        }
        return authorizationHeader;
    }
}
