package com.travel.thai.common.service;

import com.travel.thai.bbs.domain.Search;
import com.travel.thai.common.domain.Banner;
import com.travel.thai.common.domain.BannerDto;

public interface BannerService {
    Banner saveBanner(Banner banner);
    BannerDto getBannerForCategory(Search search);
    void deleteBanner(Search search);
    void modifyLink(Search search);
    void showBanner(Search search);
    void hiddenBanner(Search search);

    BannerDto getBannerForPc(String category);
    BannerDto getBannerForMobile(String category);
    void initCacheForPc();
    void initCacheForMobile();
}
