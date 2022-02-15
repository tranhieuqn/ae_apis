package com.ae.apis.entity;

import com.ae.apis.entity.enums.PaymentStatus;
import com.ae.apis.entity.enums.PaymentType;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "payment",
        indexes = {
                @Index(columnList = "id", name = "id_idx")
        }
)
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PaymentType type;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "fee")
    private Long fee;

    @Column(name = "original_fee")
    private Long originalFee;

    @Column(name = "description")
    @Lob
    private String description;

    @Column(name = "env")
    private String env;

    public boolean alreadyProcessed() {
        return !status.equals(PaymentStatus.NOT_PAYMENT_YET);
    }

}
