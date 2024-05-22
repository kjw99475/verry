package org.fullstack.verry.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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


    private LocalDate regDate;

    private LocalDateTime modifyDate;

}
