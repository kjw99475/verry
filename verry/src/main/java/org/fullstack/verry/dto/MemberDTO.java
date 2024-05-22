package org.fullstack.verry.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {
//    @PositiveOrZero
    private int memberIdx;
    @NotBlank(message = "아이디는 필수 입력입니다")
    @Size(min = 5, message = "아이디는 5~12자리로 입력해주세요")
    private String memberId;
    @NotBlank(message = "비밀번호는 필수 입력입니다")
    @Size(min = 8, message = "비밀번호는 8~20자리로 입력해주세요")
    private String pwd;
    @NotBlank(message = "이메일은 필수 입력입니다")
    private String email;
    @NotBlank(message = "이름은 필수 입력입니다.")
    private String name;
    @NotBlank(message = "우편번호는 필수 입력입니다")
    private String zipcode;
    @NotBlank(message = "주소는 필수 입력입니다")
    private String addr;
    @NotBlank(message = "상세주소는 필수 입력입니다")
    private String addrDetail;
    @NotBlank(message = "생년월일은 필수 입력입니다")
    private String birthday;
    @Builder.Default
    private String memberType = "b";
    private LocalDateTime regDate;
}
