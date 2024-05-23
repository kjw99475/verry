package org.fullstack.verry.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="tbl_trade_reply")
public class TradeReplyEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = true)
    private int tradeReplyIdx;

    @Column(length = 2000, nullable = true)
    private String comment;

    @Column(length = 20, nullable = true)
    private String memberId;

    @Column(nullable = true)
    private int tradeIdx;
}