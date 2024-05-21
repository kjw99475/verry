package org.fullstack.verry.dto;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeDTO {
    private int tradeIdx;
    private String title;
    private String content;
    private String memberId;
    private String orgFileName;
    private String saveFileName;
    private String category;
    private int price;
    private String tradeState;
    private int readCnt;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
}