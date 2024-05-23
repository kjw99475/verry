package org.fullstack.verry.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(
        name="tbl_board_reply"
//        indexes = {
//                @Index(name="board_reply_idx_tbl_board_reply_board_idx", columnList = "boardIdx")
//        }
)
public class BoardReplyEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = true)
    private int boardReplyIdx;

    @Column(length = 2000, nullable = true)
    private String comment;

    @Column(length = 20, nullable = true)
    private String memberId;


//    @ManyToOne(fetch = FetchType.LAZY)
//    private BoardEntity board;
    private int boardIdx;

//    @CreatedDate
//    @Column(name="reg_date", updatable = false, columnDefinition = "DATETIME NULL DEFAULT NOW()")
//    private LocalDateTime regDate;
}