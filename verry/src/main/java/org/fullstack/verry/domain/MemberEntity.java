package org.fullstack.verry.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="tbl_member")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = true)
    private int memberIdx;

    @Column(length = 20, nullable = true)
    private String memberId;

    @Column(length = 20, nullable = true)
    private String pwd;

    @Column(length = 30, nullable = true)
    private String email;

    @Column(length = 20, nullable = true)
    private String name;

    @Column(length = 10, nullable = true)
    private String zipcode;

    @Column(length = 20, nullable = true)
    private String addr;

    @Column(length = 20, nullable = true)
    private String addrDetail;

    @Column(length = 10, nullable = true)
    private String birthday;

    @Column(length = 10, nullable = true)
    private String memberType;

    @CreatedDate
    @Column(name="reg_date", updatable = false, columnDefinition = "DATETIME NULL DEFAULT NOW()")
    private LocalDateTime regDate;
}