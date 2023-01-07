package com.travel.thai.common.repository;

import com.travel.thai.common.domain.Banner;
import com.travel.thai.common.domain.DayStat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayStatRepository extends JpaRepository<DayStat, Long>, DayStatRepositoryCustom {
}
