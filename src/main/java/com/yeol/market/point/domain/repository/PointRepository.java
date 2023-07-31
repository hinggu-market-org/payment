package com.yeol.market.point.domain.repository;

import com.yeol.market.point.domain.Point;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Point> findByMemberId(@Param("memberId") final Long memberId);
}
