package com.dh.demo.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public record CategoryResponseDto (
         Integer id,
         String name,
         String imageUrl
){
}
