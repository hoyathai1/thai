
package com.travel.thai.common.service.Impl;

import com.travel.thai.bbs.domain.Search;
import com.travel.thai.common.domain.Banner;
import com.travel.thai.common.domain.BannerDto;
import com.travel.thai.common.repository.BannerRepository;
import com.travel.thai.common.service.BannerService;
import com.travel.thai.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public Banner saveBanner(Banner banner) {
        return bannerRepository.save(banner);
    }

    @Override
    public BannerDto getBannerForCategory(Search search) {
        return bannerRepository.getBannerForCategory(search);
    }

    @Override
    @Transactional
    public void deleteBanner(Search search) {
        bannerRepository.deleteBanner(search);
    }

    @Override
    @Transactional
    public void modifyLink(Search search) {
        bannerRepository.modifyLink(search);
    }

    @Override
    @Transactional
    public void showBanner(Search search) {
        bannerRepository.showBanner(search);
    }

    @Override
    @Transactional
    public void hiddenBanner(Search search) {
        bannerRepository.hiddenBanner(search);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "pcBanner", key= "#category")
    public BannerDto getBannerForPc(String category) {
        return bannerRepository.getBannerForPc(category);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("mBanner")
    public BannerDto getBannerForMobile(String category) {
        return bannerRepository.getBannerForMobile(category);
    }

    @Override
    @CacheEvict(value = {"pcBanner"}, allEntries = true)
    public void initCacheForPc() {
    }

    @Override
    @CacheEvict(value = {"mBanner"}, allEntries = true)
    public void initCacheForMobile() {
    }
}
