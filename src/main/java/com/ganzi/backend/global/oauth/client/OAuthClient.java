package com.ganzi.backend.global.oauth.client;

import com.ganzi.backend.global.oauth.api.dto.response.OAuthTokenResponse;

public interface OAuthClient {

    OAuthTokenResponse exchangeCodeForToken(String code);

    String getProviderName();
}
