package org.fullstack.verry.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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
public class MemberEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int memberIdx;

    @Column(length = 20, nullable = false)
    private String memberId;

    @Column(length = 20, nullable = false)
    private String pwd;

    @Column(length = 30, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 10, nullable = false)
    private String zipcode;

    @Column(length = 20, nullable = false)
    private String addr;

    @Column(length = 20, nullable = false)
    private String addrDetail;

    @Column(length = 10, nullable = false)
    private String birthday;

    @Column(length = 13, nullable = false)
    private String phone;

    @Column(length = 10, nullable = false)
    @ColumnDefault("'b'")
    private String memberType;

}