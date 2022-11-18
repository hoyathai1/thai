package com.travel.thai.bbs.repository;

import com.travel.thai.bbs.domain.BookMark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMarkRepository extends JpaRepository<BookMark, Long>, BookMarkRepositoryCustom {
}
