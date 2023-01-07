package com.travel.thai.common.repository;

import com.travel.thai.common.domain.Banner;
import com.travel.thai.common.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long>, VisitRepositoryCustom {
}
