package org.fullstack.verry.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {
    private int idx;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private String memberId;
    @NotEmpty
    private String boardType;

    private String orgFileName;
    private String saveFileName;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime regDate;
    @JsonIgnore
    private LocalDateTime modifyDate;

}
