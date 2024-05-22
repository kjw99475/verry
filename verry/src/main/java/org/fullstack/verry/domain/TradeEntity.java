package org.fullstack.verry.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="tbl_trade")
public class TradeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = true)
    private int tradeIdx;

    @Column(length = 100, nullable = true)
    private String title;

    @Column(length = 2000, nullable = true)
    private String content;

    @Column(length = 20, nullable = true)
    private String memberId;

    @Column(length = 2000, nullable = true)
    private String orgFileName;

    @Column(length = 2000, nullable = true)
    private String saveFileName;

    @Column(length = 20, nullable = true)
    private String category;

    @Column(length = 20, nullable = true)
    private int price;

    @Column(length = 20, nullable = true)
    private String tradeState;

    @Column(length = 20, nullable = true)
    private int readCnt;

    @CreatedDate
    @Column(name="reg_date", updatable = false, columnDefinition = "DATETIME NULL DEFAULT NOW()")
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name="modify_date", nullable = true, insertable = false, updatable = true)
    private LocalDateTime modifyDate;

    public void modify(String title, String content, String orgFileName, String saveFileName, String category, int price) {
        this.title = title;
        this.content = content;
        this.orgFileName = orgFileName;
        this.saveFileName = saveFileName;
        this.category = category;
        this.price = price;
        this.modifyDate = LocalDateTime.now();
    }
}