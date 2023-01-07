package com.travel.thai.common.repository;

import com.travel.thai.bbs.domain.Search;
import com.travel.thai.common.domain.Banner;
import com.travel.thai.common.domain.BannerDto;

public interface BannerRepositoryCustom {
    BannerDto getBannerForCategory(Search search);
    BannerDto getBannerForPc(String category);
    BannerDto getBannerForMobile(String category);
    void deleteBanner(Search saerch);
    void modifyLink(Search search);
    void showBanner(Search search);
    void hiddenBanner(Search search);
}