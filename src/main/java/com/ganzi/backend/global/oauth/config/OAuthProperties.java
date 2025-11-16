package com.ganzi.backend.global.oauth.config;

import com.ganzi.backend.global.code.status.ErrorStatus;
import com.ganzi.backend.global.exception.GeneralException;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "oauth")
public class OAuthProperties {

    private final Map<String, ProviderConfig> providers;

    @Getter
    @RequiredArgsConstructor
    public static class ProviderConfig {
        private final String clientId;
        private final String clientSecret;
        private final String tokenUri;
    }

    public ProviderConfig getProvider(String providerName) {
        ProviderConfig config = providers.get(providerName);
        if (config == null) {
            throw new GeneralException(ErrorStatus.UNSUPPORTED_OAUTH_PROVIDER);
        }
        return config;
    }
}
