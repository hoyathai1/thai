package com.travel.thai.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "BANNER")
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String dir;

    private String device;

    private String position;

    private String category;

    private String link;

    @ColumnDefault(value = "false")
    private boolean isDel;

    @ColumnDefault(value = "false")
    private boolean isShow;

    @ColumnDefault(value = "false")
    private boolean isBoard;
}
