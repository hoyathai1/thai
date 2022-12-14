package com.travel.thai.bbs.service;

import com.travel.thai.bbs.domain.BoardCategory;
import com.travel.thai.bbs.domain.BoardCategoryDto;
import com.travel.thai.bbs.domain.BoardType;
import com.travel.thai.bbs.repository.BoardCategoryRepository;
import com.travel.thai.bbs.repository.BoardCategoryTypeRepository;
import com.travel.thai.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
    @Cacheable("boardCategoryList")
    public List<BoardCategory> getBoardCategoryList() {

        return bcRepo.getCategoryList();
    }

    @Transactional(readOnly = true)
    @Cacheable("boardTypeList")
    public List<BoardType> getBoardTypeList(String categoryId) {
        if (StringUtils.isEmpty(categoryId)) {
            return null;
        }

        return bctRepo.getList(categoryId);
    }

    public List<BoardType> getBoardTypeListForAdmin(String categoryId) {

        return bctRepo.getListForAdmin(categoryId);
    }

    @Transactional(readOnly = true)
    @Cacheable("boardType")
    public String getBoardTypeName(String typeId, String categoryId) {
        return bctRepo.getBoardTypeName(typeId, categoryId);
    }

    public List<BoardCategory> getBoardCategoryAllList() {
        return bcRepo.getCategoryAllList();
    }

    @Transactional
    public void unuseCategory(String categoryId) {
        bcRepo.unuseCategory(categoryId);
    }

    @Transactional
    public void useCategory(String categoryId) {
        bcRepo.useCategory(categoryId);
    }

    @Transactional
    public void modifyCategory(BoardCategory boardCategory) {
        bcRepo.modifyCategory(boardCategory);
    }

    public void registerCategory(BoardCategory boardCategory) {
        boardCategory.setUse(false);
        boardCategory.setOrderBy(9);
        bcRepo.save(boardCategory);
    }

    public BoardType getBoardType(Long typeId) {
        return bctRepo.getType(typeId);
    }

    @Transactional
    public void unuseType(Long typeId) {
        bctRepo.unuseType(typeId);
    }

    @Transactional
    public void useType(Long typeId) {
        bctRepo.useType(typeId);
    }

    @Transactional
    public void modifyType(BoardType boardType) {
        bctRepo.modifyType(boardType);
    }

    public void registerType(BoardType boardType) {
        boardType.setUse(false);
        bctRepo.save(boardType);
    }

    @CacheEvict(value = {"BoardCategoryAllList", "boardCategoryList", "boardCategory"}, allEntries = true)
    public void categoryCacheInit() {
    }

    @CacheEvict(value = {"boardType", "boardTypeList"}, allEntries = true)
    public void typeCacheInit() {
    }
}
