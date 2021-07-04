package com.anz.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class ApiResponseMetaDto implements Serializable {
    private final String code;
    private final String message;
    private final Integer totalPages;
    private final Long totalItems;
}
