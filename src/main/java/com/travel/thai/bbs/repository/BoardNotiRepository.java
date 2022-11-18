package com.travel.thai.bbs.repository;

import com.travel.thai.bbs.domain.BoardNoti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardNotiRepository extends JpaRepository<BoardNoti, Long>, BoardNotiRepositoryCustom {
}
