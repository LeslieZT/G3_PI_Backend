package com.dh.demo.entity;


import com.dh.demo.dto.brand.BrandRegisterDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "brands")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    @Column(name = "creation_date")
    private LocalDate creationDate;

    public Brand(BrandRegisterDto brandRegisterDto) {
        this.name= brandRegisterDto.name();
        this.creationDate= LocalDate.from(LocalDateTime.now());
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
}
