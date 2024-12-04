package com.picpaysimple.entities.transaction;

import com.picpaysimple.entities.wallet.Wallet;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "amount")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Wallet sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Wallet receiver;

    @JoinColumn(name = "local_date_time")
    private LocalDateTime timestamp;
}
