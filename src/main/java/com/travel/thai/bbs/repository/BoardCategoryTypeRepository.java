package com.travel.thai.bbs.repository;

import com.travel.thai.bbs.domain.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCategoryTypeRepository extends JpaRepository<BoardType, Long>, BoardCategoryTypeRepositoryCustom {
}
