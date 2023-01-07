package com.travel.thai.common.repository;

import com.travel.thai.common.domain.Banner;
import com.travel.thai.common.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<Banner, Long>, BannerRepositoryCustom {
}
