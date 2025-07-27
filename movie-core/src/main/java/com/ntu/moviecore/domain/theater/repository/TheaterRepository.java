package com.ntu.moviecore.domain.theater.repository;

import com.ntu.moviecore.domain.theater.dto.TheaterDb;
import com.ntu.moviecore.domain.theater.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TheaterRepository extends JpaRepository<Theater, Long> {

    @Query(value = """
            SELECT mp.code AS provinceCode, mp.name AS provinceName,
                   mw.id AS wardId, mw.name AS wardName,
                   tt.id AS theaterId, tt.name AS theaterName, tt.is_default AS isDefault
            FROM mt_province mp
            JOIN mt_ward mw
            ON mp.province_code = mw.province_code
            JOIN tbl_theater tt
            ON mw.id = tt.ward_id
            ORDER BY mp.province_code;""", nativeQuery = true)
    List<TheaterDb> getTheaters();
}
