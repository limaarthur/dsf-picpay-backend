package com.picpaysimple.services;

import com.picpaysimple.dtos.WalletDTO;
import com.picpaysimple.entities.wallet.Wallet;
import com.picpaysimple.entities.wallet.WalletType;
import com.picpaysimple.repositories.WalletRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public void validateTransaction(Wallet sender, BigDecimal amount) throws Exception {
        if (sender.getWalletType() == WalletType.MERCHANT) {
            throw new Exception("Usuário não autorizado a realizar transação");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente");
        }
    }

    public Wallet findWalletById(Long id) throws Exception {
        return this.walletRepository.findWalletById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
    }

    public Wallet createWallet(WalletDTO data) {
        Wallet newWallet = new Wallet(data);
        this.saveWallet(newWallet);
        return newWallet;
    }

    public List<Wallet> getAllWallets() {
        return this.walletRepository.findAll();
    }

    public void saveWallet(Wallet wallet) {
        this.walletRepository.save(wallet);
    }
}
