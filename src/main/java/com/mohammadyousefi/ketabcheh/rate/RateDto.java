package com.mohammadyousefi.ketabcheh.rate;

import lombok.Data;

@Data
public class RateDto {
    private Long userId;
    private Long bookId;
    private int rate;
}
