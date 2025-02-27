package com.dh.demo.dto.product;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public record ProductImageResponseDto (
         Integer id,
         String url
){
}
