package com.picpaysimple.domain.transaction;

import com.picpaysimple.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transactions")
@Table(name = "transaction")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @ManyToMany
    @JoinColumn(name = "sender_id")
    private User sender;

    @JoinColumn(name = "receiver_id")
    @ManyToMany
    private User receiver;

    private LocalDateTime timestamp;
}
