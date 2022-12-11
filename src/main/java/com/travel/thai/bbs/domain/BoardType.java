package com.travel.thai.bbs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "BOARD_TYPE")
public class BoardType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private String name;

//    private String categoryId;

    @JsonIgnore
    @JoinColumn(name="category_id", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private BoardCategory boardCategory;

    private int orderBy;

    @ColumnDefault(value = "false")
    private boolean isUse;

}
