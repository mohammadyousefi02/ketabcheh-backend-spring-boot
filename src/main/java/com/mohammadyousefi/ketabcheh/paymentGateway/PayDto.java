package com.mohammadyousefi.ketabcheh.paymentGateway;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PayDto {
    @NotNull
    private int cardNumberFirst;
    @NotNull
    private int cardNumberSecond;
    @NotNull
    private int cardNumberThird;
    @NotNull
    private int cardNumberFourth;
    @NotNull
    private Long cardPassword;
    @NotNull
    private int cardCvv2;
    @NotNull
    private int cardExpirationMonth;
    @NotNull
    private int cardExpirationYear;
    @NotNull
    @Email
    private String email;
}
