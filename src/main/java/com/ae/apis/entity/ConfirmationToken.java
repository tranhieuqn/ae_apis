package com.ae.apis.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
        name = "confirmation_token",
        indexes = {
                @Index(columnList = "id", name = "id_idx"),
                @Index(columnList = "user_id", name = "user_id_idx")
        }
)
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "expire_date")
    private Date expireDate;

    @Column(name = "type")
    private String type;
}
