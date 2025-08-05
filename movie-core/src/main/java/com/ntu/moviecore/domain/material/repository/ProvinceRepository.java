package com.ntu.moviecore.domain.material.repository;

import com.ntu.moviecore.domain.material.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceRepository extends JpaRepository<Province, Long> {
}
