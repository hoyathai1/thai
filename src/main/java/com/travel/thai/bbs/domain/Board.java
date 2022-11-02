package com.travel.thai.bbs.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "BOARD")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ip;

    private String type;

    private String title;

    @Lob
    private String contents;

    @Lob
    private String contentsTxt;

    private String author;

    private String password;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @ColumnDefault("1")
    private int view;

    @ColumnDefault(value = "false")
    private boolean isDel;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createDate = LocalDateTime.now();
        this.updateDate = this.createDate;
    }

    @PreUpdate // 데이터 수정이 이루어질때 사전 작업
    public void preUpdate() {
        this.updateDate = LocalDateTime.now();
    }

    @Builder
    public Board(Long id, String title, String author, LocalDateTime createDate, int view) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.createDate = createDate;
        this.view = view;
    }

    @Builder
    public Board(Long id, String title, String author, String contents, LocalDateTime createDate, int view) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.contents = contents;
        this.createDate = createDate;
        this.view = view;
    }

    @Builder
    public Board(Long id, String title, String author, LocalDateTime createDate, int view, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.createDate = createDate;
        this.view = view;
        this.comments = comments;
    }

    @Builder
    public Board(Long id, String title, String author, String contents, LocalDateTime createDate, int view, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.contents = contents;
        this.createDate = createDate;
        this.view = view;
        this.comments = comments;
    }
}
