package com.ae.apis.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_vnp")
public class PaymentVNP extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "payment_id", foreignKey = @ForeignKey(name = "fk__payment_vnp__payment"), nullable = false)
    private Payment payment;

    @Basic(optional = false)
    @Column(name = "vnp_ip_addr")
    private String vnpIpAddr;

    @Basic(optional = false)
    @Column(name = "vnp_txn_ref")
    private String vnpTxnRef;

    @Basic(optional = false)
    @Column(name = "vnp_tmn_code")
    private String vnpTmnCode;

    @Basic(optional = false)
    @Column(name = "vnp_amount")
    private BigDecimal vnpAmount;

    @Basic(optional = false)
    @Column(name = "vnp_command")
    private String vnpCommand;

    @Basic(optional = false)
    @Column(name = "vnp_create_date")
    private Date vnpCreateDate;

    @Basic(optional = false)
    @Column(name = "vnp_curr_code")
    private String vnpCurrCode;

    @Basic(optional = false)
    @Column(name = "vnp_locale")
    private String vnpLocale;

    @Basic(optional = false)
    @Column(name = "vnp_version")
    private String vnpVersion;

    @Basic(optional = false)
    @Column(name = "vnp_order_type")
    private String vnpOrderType;

    @Basic(optional = false)
    @Column(name = "vnp_order_info")
    private String vnpOrderInfo;

    @Basic(optional = false)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "vnp_bank_code")
    private String vnpBankCode;

    @Column(name = "vnp_bank_tran_no")
    private String vnpBankTranNo;

    @Column(name = "vnp_card_type")
    private String vnpCardType;

    @Column(name = "vnp_pay_date")
    private Date vnpPayDate;

    @Column(name = "vnp_response_code")
    private String vnpResponseCode;

    @Column(name = "vnp_transaction_no")
    private String vnpTransactionNo;
}