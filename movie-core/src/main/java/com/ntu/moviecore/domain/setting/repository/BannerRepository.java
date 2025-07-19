package com.ntu.moviecore.domain.setting.repository;

import com.ntu.moviecore.domain.setting.entity.Banner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, Long> {

    @Query(value ="SELECT ba FROM Banner ba ORDER BY ba.priority")
    List<Banner> getBanners(Pageable pageable);
}
