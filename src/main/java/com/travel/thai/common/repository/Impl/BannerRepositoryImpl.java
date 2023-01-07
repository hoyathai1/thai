package com.travel.thai.common.repository.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.thai.bbs.domain.Search;
import com.travel.thai.common.domain.Banner;
import com.travel.thai.common.domain.BannerDto;
import com.travel.thai.common.repository.BannerRepositoryCustom;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.travel.thai.common.domain.QBanner.banner;

@RequiredArgsConstructor
public class BannerRepositoryImpl implements BannerRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private static final String TOP = "TOP";
    private static final String BOTTOM = "BOTTOM";
    private static final String LEFT = "LEFT";
    private static final String RIGHT = "RIGHT";

    @Override
    public BannerDto getBannerForCategory(Search search) {
        List<Banner> list = queryFactory
                .select(banner)
                .from(banner)
                .where(banner.category.eq(search.getCategory()).and(banner.device.eq(search.getDevice())))
                .fetch();

        BannerDto dto = new BannerDto();
        list.forEach(
                x->{
                    if(TOP.equals(x.getPosition())) {
                        dto.setTopBanner(x);
                    } else if(BOTTOM.equals(x.getPosition())) {
                        dto.setBottomBanner(x);
                    } else if(LEFT.equals(x.getPosition())) {
                        dto.setLeftBanner(x);
                    } else if(RIGHT.equals(x.getPosition())) {
                        dto.setRightBanner(x);
                    }
                }
        );

        return dto;
    }

    @Override
    public BannerDto getBannerForPc(String category) {
        List<Banner> list = queryFactory
                .select(banner)
                .from(banner)
                .where(banner.category.eq(category).and(banner.device.eq("PC")))
                .fetch();

        BannerDto dto = new BannerDto();
        list.forEach(
                x->{
                    if(TOP.equals(x.getPosition())) {
                        dto.setTopBanner(x);
                    } else if(BOTTOM.equals(x.getPosition())) {
                        dto.setBottomBanner(x);
                    } else if(LEFT.equals(x.getPosition())) {
                        dto.setLeftBanner(x);
                    } else if(RIGHT.equals(x.getPosition())) {
                        dto.setRightBanner(x);
                    }
                }
        );

        return dto;
    }

    @Override
    public BannerDto getBannerForMobile(String category) {
        List<Banner> list = queryFactory
                .select(banner)
                .from(banner)
                .where(banner.category.eq(category).and(banner.device.eq("MOBILE")))
                .fetch();

        BannerDto dto = new BannerDto();
        list.forEach(
                x->{
                    if(TOP.equals(x.getPosition())) {
                        dto.setTopBanner(x);
                    } else if(BOTTOM.equals(x.getPosition())) {
                        dto.setBottomBanner(x);
                    } else if(LEFT.equals(x.getPosition())) {
                        dto.setLeftBanner(x);
                    } else if(RIGHT.equals(x.getPosition())) {
                        dto.setRightBanner(x);
                    }
                }
        );

        return dto;
    }

    @Override
    public void deleteBanner(Search search) {
        queryFactory.delete(banner)
                .where(banner.position.eq(search.getPosition()).and(banner.category.eq(search.getCategory())).and(banner.device.eq(search.getDevice())))
                .execute();
//        queryFactory.update(banner)
//                .set(banner.isDel, true)
//                .where(banner.position.eq(search.getPosition()).and(banner.category.eq(search.getCategory())).and(banner.device.eq(search.getDevice())))
//                .execute();
    }

    @Override
    public void modifyLink(Search search) {
        queryFactory.update(banner)
                .set(banner.link, search.getLink())
                .set(banner.isBoard, search.isBoard())
                .where(banner.position.eq(search.getPosition()).and(banner.category.eq(search.getCategory())).and(banner.device.eq(search.getDevice())))
                .execute();
    }

    @Override
    public void showBanner(Search search) {
        queryFactory
                .update(banner)
                .set(banner.isShow, true)
                .where(banner.position.eq(search.getPosition()).and(banner.category.eq(search.getCategory())).and(banner.device.eq(search.getDevice())))
                .execute();
    }

    @Override
    public void hiddenBanner(Search search) {
        queryFactory
                .update(banner)
                .set(banner.isShow, false)
                .where(banner.position.eq(search.getPosition()).and(banner.category.eq(search.getCategory())).and(banner.device.eq(search.getDevice())))
                .execute();
    }
}