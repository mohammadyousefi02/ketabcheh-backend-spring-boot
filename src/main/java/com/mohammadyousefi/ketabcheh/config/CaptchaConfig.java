package com.mohammadyousefi.ketabcheh.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "google.recaptcha")
public class CaptchaConfig {
    private String siteKey;
    private String secretKey;
}
