package com.picpaysimple.controllers;

import com.picpaysimple.dtos.WalletDTO;
import com.picpaysimple.entities.wallet.Wallet;
import com.picpaysimple.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping
    public ResponseEntity<Wallet> createWallet(@RequestBody WalletDTO walletDTO) {
        Wallet newWallet = walletService.createWallet(walletDTO);
        return new ResponseEntity<>(newWallet, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Wallet>> getAllWallets() {
        List<Wallet> wallets = this.walletService.getAllWallets();
        return new ResponseEntity<>(wallets, HttpStatus.OK);
    }
}
