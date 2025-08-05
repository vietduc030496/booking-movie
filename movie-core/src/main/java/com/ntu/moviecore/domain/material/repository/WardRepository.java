package com.ntu.moviecore.domain.material.repository;

import com.ntu.moviecore.domain.material.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WardRepository extends JpaRepository<Ward, Long> {

    @Query("SELECT w FROM Ward w WHERE w.province.id = :provinceId")
    List<Ward> findByProvinceId(@Param("provinceId") Long provinceId);
}
