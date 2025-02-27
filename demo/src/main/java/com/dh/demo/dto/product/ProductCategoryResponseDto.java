package com.dh.demo.dto.product;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public record ProductCategoryResponseDto (
         Integer id,
         String name
){
}
