package com.travel.thai.bbs.repository;

import com.travel.thai.bbs.domain.BoardInform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardInformRepository extends JpaRepository<BoardInform, Long>, BoardInformRepositoryCustom {
}
