package com.ae.apis.entity;

import com.ae.apis.entity.enums.MediaDetailStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false, exclude = {"id", "status"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "media_detail")
public class MediaDetail extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "media_id")
  private Media media;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "url")
  private String url;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private MediaDetailStatus status;
}
