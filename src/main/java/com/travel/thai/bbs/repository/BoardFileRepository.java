package com.travel.thai.bbs.repository;

import com.travel.thai.bbs.domain.Board;
import com.travel.thai.bbs.domain.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long>, BoardFileRepositoryCustom {
}
