package com.mohammadyousefi.ketabcheh.profile;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WalletDto {
    @NotNull
    private Long wallet;
}
