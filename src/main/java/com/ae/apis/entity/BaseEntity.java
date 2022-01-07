package com.ae.apis.entity;


import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {

  @Column(name = "created_date", nullable = false, updatable = false)
  @CreatedDate
  protected Instant createdDate;

  @Column(name = "modified_date")
  @LastModifiedDate
  protected Instant modifiedDate;
}