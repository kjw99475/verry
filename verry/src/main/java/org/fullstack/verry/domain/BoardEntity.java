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
@Table(name="tbl_board")
public class BoardEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = true)
    private int idx;

    @Column(length = 100, nullable = true)
    private String title;

    @Column(length = 2000, nullable = true)
    private String content;

    @Column(length = 20, nullable = true)
    private String memberId;

    @Column(length = 2, nullable = true)
    private String boardType;

    @Column(length = 2000, nullable = true)
    private String orgFileName;

    @Column(length = 2000, nullable = true)
    private String saveFileName;


    public void modify(String title, String content, String orgFileName, String saveFileName) {
        this.title = title;
        this.content = content;
        this.orgFileName = orgFileName;
        this.saveFileName = saveFileName;
        super.setModify_date(LocalDateTime.now());
    }
}