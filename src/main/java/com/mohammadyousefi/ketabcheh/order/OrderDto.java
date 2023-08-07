package com.mohammadyousefi.ketabcheh.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderDto {
    @NotNull
    private String name;
    @NotNull
    private Long zipcode;
    @NotNull
    private String province;
    @NotNull
    private String city;
    @NotNull
    private String address;
    private Long userId;
}
