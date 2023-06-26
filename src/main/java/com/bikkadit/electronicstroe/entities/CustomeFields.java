package com.bikkadit.electronicstroe.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@ToString
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public class CustomeFields implements Serializable {
    @Column(name = "islive ")
    private boolean live;

    @Column(name = "created_By")
    @CreatedBy
    private String  createdBy;

    @Column(name = "created_Date",updatable = false)

    private Date createOn;

//    @Column(name = "modified_by")
//    @LastModifiedBy
//    private String  lastModifiedBy;
//
//    @Column(name = "update_time",updatable = false)
//    private LocalTime modifiedOn;






}
