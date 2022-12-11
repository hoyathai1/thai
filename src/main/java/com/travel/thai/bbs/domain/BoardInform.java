package com.travel.thai.bbs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "BOARDINFORM")
public class BoardInform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

    private String type;

    private String title;

    private String userId;

    @Lob
    private String contents;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @ColumnDefault("1")
    private int view;

    @ColumnDefault(value = "false")
    private boolean isDel;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createDate = LocalDateTime.now();
        this.updateDate = this.createDate;
    }

    @PreUpdate // 데이터 수정이 이루어질때 사전 작업
    public void preUpdate() {
        this.updateDate = LocalDateTime.now();
    }
}
