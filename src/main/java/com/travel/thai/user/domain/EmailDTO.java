package com.travel.thai.user.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailDTO {
    private String address;
    private String title;
    private String message;
}
