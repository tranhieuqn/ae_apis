package com.ae.apis.entity;

import com.ae.apis.entity.enums.PaymentType;
import com.ae.apis.entity.enums.RefundStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "payment_refund_history",
        indexes = {
                @Index(columnList = "id", name = "id_idx")
        }
)
public class PaymentRefundHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "payment_id")
    private long paymentId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RefundStatus status;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "description")
    @Lob
    private String description;
}
