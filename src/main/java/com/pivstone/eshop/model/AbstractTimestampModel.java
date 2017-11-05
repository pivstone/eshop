package com.pivstone.eshop.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/5.
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractTimestampModel {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, insertable = true)
    @CreatedDate
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updateAt;
}
