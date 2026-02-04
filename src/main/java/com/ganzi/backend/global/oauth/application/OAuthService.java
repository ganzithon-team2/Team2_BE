package com.ganzi.backend.global.oauth.application;

import com.ganzi.backend.global.code.status.ErrorStatus;
import com.ganzi.backend.global.exception.GeneralException;
import com.ganzi.backend.global.oauth.api.dto.response.LoginResponse;
import com.ganzi.backend.global.oauth.api.dto.response.OAuthTokenResponse;
import com.ganzi.backend.global.oauth.client.OAuthClient;
import com.ganzi.backend.global.security.jwt.JwtService;
import com.ganzi.backend.global.security.userdetails.CustomUserDetails;
import com.ganzi.backend.user.User;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class OAuthService {

    private final IdTokenService idTokenService;
    private final JwtService jwtService;
    private final Map<String, OAuthClient> oAuthClientMap;

    public OAuthService(
            List<OAuthClient> oAuthClients,
            IdTokenService idTokenService,
            JwtService jwtService
    ) {
        this.oAuthClientMap = oAuthClients.stream()
                .collect(Collectors.toMap(
                        OAuthClient::getProviderName,
                        Function.identity()
                ));
        this.idTokenService = idTokenService;
        this.jwtService = jwtService;
    }

    @Transactional
    public LoginResponse loginWithCode(String provider, String code) {
        OAuthClient oAuthClient = getOAuthClient(provider);

        OAuthTokenResponse tokenResponse = oAuthClient.exchangeCodeForToken(code);

        CustomUserDetails userDetails = idTokenService.loadUserByIdToken(tokenResponse.idToken());
        User user = userDetails.getUser();

        String accessToken = jwtService.createAccessToken(user.getEmail(), user.getId());
        String refreshToken = jwtService.createRefreshToken();

        jwtService.updateRefreshToken(user.getEmail(), refreshToken);

        return new LoginResponse(accessToken, refreshToken, user.getId(), user.getNickname());
    }

    private OAuthClient getOAuthClient(String provider) {
        OAuthClient client = oAuthClientMap.get(provider.toLowerCase());
        if (client == null) {
            throw new GeneralException(ErrorStatus.UNSUPPORTED_OAUTH_PROVIDER);
        }
        return client;
    }
}
