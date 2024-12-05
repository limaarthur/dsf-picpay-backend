package com.picpaysimple.repositories;

import com.picpaysimple.dtos.WalletDTO;
import com.picpaysimple.entities.wallet.Wallet;
import com.picpaysimple.entities.wallet.WalletType;
import jakarta.persistence.EntityManager;
import org.hibernate.usertype.UserType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class WalletRepositoryTest {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get Wallet successfully from DB")
    void findWalletByDocumentSuccess() {
        String document = "99999999901";
        WalletDTO data = new WalletDTO("João da Silva", document, new BigDecimal(10), "teste@gmail.com", "4444", WalletType.COMMON);
        this.createWallet(data);

        Optional<Wallet> foundedWallet = this.walletRepository.findWalletByDocument(document);

        assertThat(foundedWallet.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get Wallet from DB when user not exists")
    void findWalletByDocumentFailure() {
        String document = "99999999901";

        Optional<Wallet> foundedWallet = this.walletRepository.findWalletByDocument(document);

        assertThat(foundedWallet.isEmpty()).isTrue();
    }

    private Wallet createWallet(WalletDTO data) {
        Wallet newWallet = new Wallet(data);
        this.entityManager.persist(newWallet);
        return newWallet;
    }
}