package com.ae.apis.entity;

import com.ae.apis.entity.enums.PropertyStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "property")
public class Property extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "thumbnail")
  private String thumbnail;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private PropertyStatus status;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "media_id")
  private Media media;
}
