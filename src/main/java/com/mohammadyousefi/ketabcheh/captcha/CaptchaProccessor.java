package com.mohammadyousefi.ketabcheh.captcha;

import com.mohammadyousefi.ketabcheh.config.CaptchaConfig;
import com.mohammadyousefi.ketabcheh.paymentGateway.GoogleCaptchaResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.net.URI;

@Component
public class CaptchaProccessor {

    private static final String GOOGLE_CAPTCHA_VERIFY_API = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    private final RestOperations restTemplate;
    private final CaptchaConfig captchaConfig;

    public CaptchaProccessor(RestOperations restTemplate, CaptchaConfig captchaConfig) {
        this.restTemplate = restTemplate;
        this.captchaConfig = captchaConfig;
    }

    public Boolean proccessResponse(String response) {
        URI verifyUrl = URI.create(String.format(GOOGLE_CAPTCHA_VERIFY_API, captchaConfig.getSecretKey(), response));
        GoogleCaptchaResponse googleCaptchaResponse = restTemplate.getForObject(verifyUrl, GoogleCaptchaResponse.class);
        if(googleCaptchaResponse == null) return false;
        return googleCaptchaResponse.getSuccess();
    }
}
