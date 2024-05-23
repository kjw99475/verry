package org.fullstack.verry.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank(message = "아이디는 필수 입력사항입니다")
    @Pattern(regexp = "^[a-z0-9]{4,12}", message = "5~12자 이내의 영어 소문자 및 숫자만 입력이 가능합니다.")
    private String memberId;
    @NotBlank(message = "비밀번호는 필수 입력사항입니다")
    @Pattern(regexp="^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$", message = "영문 + 숫자 + 특수문자 조합으로 최소 8자리 이상만 허용됩니다.")
    private String pwd;
    @NotBlank(message = "이메일은 필수 입력사항입니다")
    private String email;
    @NotBlank(message = "이름은 필수 입력사항입니다")
    private String name;
    @NotBlank(message = "우편번호는 필수 입력사항입니다")
    private String zipcode;
    @NotBlank(message = "주소는 필수 입력사항입니다")
    private String addr;
    @NotBlank(message = "상세주소는 필수 입력사항입니다")
    private String addrDetail;
    @NotBlank(message = "생년월일은 필수 입력사항입니다")
    private String birthday;
    @NotBlank(message = "전화번호는 필수 입력사항입니다")
    private String phone;
    @Builder.Default
    private String memberType = "b";
    private LocalDateTime regDate;
}
