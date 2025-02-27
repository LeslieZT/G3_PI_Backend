package com.dh.demo.dto.Generic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public record PaginationResponseDto<T>(
        List<T> data,
        int page,
        int size,
        int total
) {}

