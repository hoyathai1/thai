package com.travel.thai.bbs.repository;

import com.travel.thai.bbs.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long>, LikesRepositoryCustom {
}
