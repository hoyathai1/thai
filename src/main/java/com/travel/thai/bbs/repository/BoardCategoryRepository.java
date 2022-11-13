package com.travel.thai.bbs.repository;

import com.travel.thai.bbs.domain.BoardCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCategoryRepository extends JpaRepository<BoardCategory, Long>, BoardCategoryRepositoryCustom {
}
