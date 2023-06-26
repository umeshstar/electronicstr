package com.bikkadit.electronicstroe.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CustomeFields {
//    @Column(name = "is active ")
//    private String  isActive;
//
//    @Column(name = "created_By")
//    @CreatedBy
//    private String  createdBy;
//
//    @Column(name = "created_Date",updatable = false)
//    @CreationTimestamp
//    private String  createOn;
//
//    @Column(name = "modified_by")
//    @LastModifiedBy
//    private String  lastModifiedBy;
//
//    @Column(name = "update_time",updatable = false)
//    @UpdateTimestamp
//    private String  modifiedOn;
//
//
//



}
