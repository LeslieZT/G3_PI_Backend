package com.dh.demo.repository;

import com.dh.demo.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBrandRepository extends JpaRepository<Brand, Integer> {
    List<Brand> findAll();
}
