package com.travel.thai.bbs.repository;

import com.travel.thai.bbs.domain.BoardFile;
import com.travel.thai.bbs.domain.BoardInform;
import com.travel.thai.bbs.domain.BoardInformFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardInformFileRepository extends JpaRepository<BoardInformFile, Long>, BoardInformFileRepositoryCustom {
}
