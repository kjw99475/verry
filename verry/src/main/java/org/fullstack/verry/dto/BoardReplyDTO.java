package org.fullstack.verry.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardReplyDTO {
    private int boardReplyIdx;

    private int boardIdx;
    @NotEmpty
    private String comment;
    @NotEmpty
    private String memberId;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate regDate;
}
