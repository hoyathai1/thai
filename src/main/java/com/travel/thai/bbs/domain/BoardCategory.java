package com.travel.thai.bbs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "BOARD_CATEGORY")
public class BoardCategory {
    @Id
    private String id;

    private String name;

    @ColumnDefault(value = "false")
    private boolean isUse;

    private int orderBy;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "boardCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardType> boardTypes;
}
