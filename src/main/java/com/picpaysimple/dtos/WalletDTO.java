package com.picpaysimple.dtos;

import com.picpaysimple.entities.wallet.WalletType;

import java.math.BigDecimal;

public record WalletDTO(String fullName, String document, BigDecimal balance, String email, String password, WalletType walletType) {
}
