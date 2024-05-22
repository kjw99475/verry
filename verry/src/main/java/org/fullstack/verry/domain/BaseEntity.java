package org.fullstack.verry.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value={AuditingEntityListener.class})
public abstract class BaseEntity {
    @CreatedDate
    @Column(name="regDate", updatable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDate regDate;
    @LastModifiedDate
    @Column(name="modify_date", nullable = true, insertable = false, updatable = true)
    private LocalDateTime modify_date;
    public void setModify_date(LocalDateTime modify_date){
        this.modify_date = LocalDateTime.now();
    }
}
