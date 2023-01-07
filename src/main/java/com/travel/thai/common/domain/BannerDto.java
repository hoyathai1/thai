package com.travel.thai.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Data
public class BannerDto {
    private Banner topBanner;
    private Banner bottomBanner;
    private Banner leftBanner;
    private Banner rightBanner;

}
