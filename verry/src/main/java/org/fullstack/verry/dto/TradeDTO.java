package org.fullstack.verry.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "제목을 입력하세요.")
    private String title;
    @NotBlank(message = "내용을 입력하세요.")
    private String content;
    private String memberId;
    private String orgFileName;
    private String saveFileName;
    @NotBlank(message = "카테고리를 선택하세요.")
    private String category;
    @NotNull(message = "가격을 입력하세요")
    @PositiveOrZero(message = "적절한 가격을 입력해주세요")
    private int price;
    private String tradeState;
    private int readCnt;
    private LocalDateTime regDate;
    private LocalDateTime modifyDate;
}