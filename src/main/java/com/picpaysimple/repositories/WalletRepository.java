package com.picpaysimple.repositories;

import com.picpaysimple.entities.wallet.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet>findWalletByDocument(String document);
    Optional<Wallet>findWalletById(Long id);
}
