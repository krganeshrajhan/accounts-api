package com.anz.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class ApiResponseDto<T> implements Serializable {
    private final ApiResponseMetaDto meta;
    private final T data;
}
