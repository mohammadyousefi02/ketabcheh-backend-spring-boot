package com.mohammadyousefi.ketabcheh.order;

import lombok.Data;

@Data
public class OrderDto {
    private String name;
    private Long zipcode;
    private String province;
    private String city;
    private String address;
    private Long userId;
}
