package com.ntu.persistence.theater.repository;

import com.ntu.moviecore.domain.theater.readmodel.TheaterSummary;
import com.ntu.persistence.theater.entity.TheaterJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TheaterRepository extends JpaRepository<Theater, Long> {

    @Query(value = """
            SELECT mp.province_code AS provinceCode, mp.name AS provinceName,
                   mw.id AS wardId, mw.name AS wardName,
                   tt.id AS theaterId, tt.name AS theaterName, tt.is_default AS isDefault,
                   tt.address AS theaterAddress, tt.hotline AS theaterHotline
            FROM mt_province mp
            JOIN mt_ward mw
            ON mp.province_code = mw.province_code
            JOIN tbl_theater tt
            ON mw.id = tt.ward_id
            ORDER BY mp.province_code;""",
            nativeQuery = true)
    List<TheaterDb> getTheaters();

    @Query(value = """
            SELECT mp.province_code AS provinceCode, mp.name AS provinceName,
                   mw.id AS wardId, mw.name AS wardName,
                   tt.id AS theaterId, tt.name AS theaterName, tt.is_default AS isDefault,
                   tt.address AS theaterAddress, tt.hotline AS theaterHotline
            FROM mt_province mp
            JOIN mt_ward mw
            ON mp.province_code = mw.province_code
            JOIN tbl_theater tt
            ON mw.id = tt.ward_id
            WHERE tt.id = :theaterId""",
            nativeQuery = true)
    Optional<TheaterDb> getTheaterById(@Param("theaterId") long theaterId);

    @Modifying
    @Query("UPDATE Theater t SET t.isDefault = false WHERE t.isDefault = true AND t.id <> :theaterId")
    void clearDefaultExcept(@Param("theaterId") long theaterId);
}
