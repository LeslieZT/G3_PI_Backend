package com.dh.demo.dto.Generic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public record ResponseDto<T>(
        T data
) {}

