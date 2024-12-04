package com.picpaysimple.entities.wallet;

import com.picpaysimple.dtos.WalletDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.usertype.UserType;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_wallets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "full_name")
    private String fullName;

    @Column(unique = true)
    @JoinColumn(name = "document")
    private String document;

    @Column(unique = true)
    @JoinColumn(name = "email")
    private String email;

    @JoinColumn(name = "password")
    private String password;

    @JoinColumn(name = "balance")
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "wallet_type")
    private WalletType walletType;

    public Wallet(WalletDTO data) {
        this.fullName = data.fullName();
        this.document = data.document();
        this.balance = data.balance();
        this.walletType = data.walletType();
        this.password = data.password();
        this.email = data.email();
    }
}
