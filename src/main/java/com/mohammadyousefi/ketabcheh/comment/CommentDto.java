package com.mohammadyousefi.ketabcheh.comment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentDto {
    @NotNull
    @NotEmpty
    private String message;
}
