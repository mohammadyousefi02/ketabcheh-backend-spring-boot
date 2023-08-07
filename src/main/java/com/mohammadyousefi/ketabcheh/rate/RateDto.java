package com.mohammadyousefi.ketabcheh.rate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RateDto {
    private Long userId;
    private Long bookId;
    @NotNull
    private int rate;
}
