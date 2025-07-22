package com.ntu.moviecore.domain.theater.repository;

import com.ntu.moviecore.domain.theater.dto.TheaterDb;
import com.ntu.moviecore.domain.theater.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater, Long> {

    @Query(value = """
            SELECT mp.code as provinceCode, mp.name as provinceName,
                   mw.id as wardId, mw.name as wardName,
                   tt.id as theaterId, tt.name as theaterName
            FROM mt_province mp
            JOIN mt_ward mw
            ON mp.province_code = mw.province_code
            JOIN tbl_theater tt
            ON mw.id = tt.ward_id
            ORDER BY mp.province_code;""", nativeQuery = true)
    List<TheaterDb> getTheaters();
}
