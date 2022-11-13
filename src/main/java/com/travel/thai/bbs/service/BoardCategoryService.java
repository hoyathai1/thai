package com.travel.thai.bbs.service;

import com.travel.thai.bbs.domain.BoardCategory;
import com.travel.thai.bbs.domain.BoardType;
import com.travel.thai.bbs.repository.BoardCategoryRepository;
import com.travel.thai.bbs.repository.BoardCategoryTypeRepository;
import com.travel.thai.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardCategoryService {
    @Autowired
    private BoardCategoryTypeRepository bctRepo;

    @Autowired
    private BoardCategoryRepository bcRepo;

    @Transactional(readOnly = true)
    @Cacheable("boardCategory")
    public BoardCategory getBoardCategory(String categoryId) {
        if (StringUtils.isEmpty(categoryId)) {
            return null;
        }

        return bcRepo.getCategoryInfo(categoryId);
    }

    @Transactional(readOnly = true)
    @Cacheable("boardType")
    public List<BoardType> getBoardTypeList(String categoryId) {
        if (StringUtils.isEmpty(categoryId)) {
            return null;
        }

        return bctRepo.getList(categoryId);
    }

    public String getBoardTypeName(String typeId) {
        return bctRepo.getBoardTypeName(typeId);
    }
}
