package com.dh.demo.dto.Generic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record ResponseDto<T>(
        T data
) {}

