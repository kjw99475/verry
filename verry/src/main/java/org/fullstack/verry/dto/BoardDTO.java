package org.fullstack.verry.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
    @NotEmpty(message = "제목을 입력해 주세요.")
    @Size( max=100, message = "제목은 100자 이내로 작성해 주세요.")
    private String title;
    @NotEmpty(message = "내용을 입력해 주세요.")
    @Size( max=2000, message = "내용은 2000자 이내로 작성해 주세요.")
    private String content;
    @NotEmpty(message="로그인 해 주세요.")
    private String memberId;

    private String boardType;

    private String orgFileName;
    private String saveFileName;


    private LocalDate regDate;

    private LocalDateTime modifyDate;

}
