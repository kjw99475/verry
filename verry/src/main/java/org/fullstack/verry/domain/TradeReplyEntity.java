package org.fullstack.verry.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="tbl_trade_reply")
public class TradeReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = true)
    private int tradeReplyIdx;

    @Column(length = 2000, nullable = true)
    private String comment;

    @Column(length = 20, nullable = true)
    private String memberId;

    @CreatedDate
    @Column(name="reg_date", updatable = false, columnDefinition = "DATETIME NULL DEFAULT NOW()")
    private LocalDateTime regDate;
    
    @Column(nullable = true)
    private int tradeIdx;
}