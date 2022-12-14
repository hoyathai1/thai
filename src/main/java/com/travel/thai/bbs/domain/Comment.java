package com.travel.thai.bbs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "COMMENT")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ip;

    private String userId;

    private boolean isUser;

    private String author;

    private String password;

    @JsonIgnore
    @JoinColumn(name="board_id", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @Column(nullable = false)
    private String content;

    private LocalDateTime createDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> children = new ArrayList<>();

    private Long upper;

    @ColumnDefault(value = "false")
    private boolean isDel;

    // 데이터 생성이 이루어질때 사전 작업
    @PrePersist
    public void prePersist() {
        this.createDate = LocalDateTime.now();
    }

    @Builder
    public Comment(Long id, String author, String content, LocalDateTime createDate) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.createDate = createDate;
    }

    @Builder
    public Comment(Long id, String author, String content, LocalDateTime createDate, Comment comment) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.createDate = createDate;
        this.children.add(comment);
    }

    public String getIp() {
        if (isUser) {
            return "";
        } else {
            String[] arr = ip.split("\\.");

            return arr[0] + "." +arr[1];
        }
    }
}
