package com.picpaysimple.services;

import com.picpaysimple.dtos.TransactionDTO;
import com.picpaysimple.entities.wallet.Wallet;
import com.picpaysimple.entities.wallet.WalletType;
import com.picpaysimple.repositories.TransactionRepository;
import org.hibernate.usertype.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private WalletService walletService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AuthorizationService authorizationService;

    @Mock
    private NotificationService notificationService;

    @Autowired
    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should create transaction successfully when everything is OK")
    void createTransaction() throws Exception {
        Wallet sender = new Wallet(1L, "Maria Silva", "99999999901", "maria.silva@gmail.com", "maria12345", new BigDecimal(10), WalletType.COMMON);
        Wallet receiver = new Wallet(2L, "Joao da Costa", "65845202055", "joaodacosta@gmail.com", "joao65484", new BigDecimal(10), WalletType.COMMON);

        when(walletService.findWalletById(1L)).thenReturn(sender);
        when(walletService.findWalletById(2L)).thenReturn(receiver);

        when(authorizationService.authorizeTransaction(any(), any())).thenReturn(true);

        TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1L, 2L);
        transactionService.createTransaction(request);

        verify(transactionRepository, times(1)).save(any());

        sender.setBalance(new BigDecimal(0));
        verify(walletService, times(1)).saveWallet(sender);

        receiver.setBalance(new BigDecimal(20));
        verify(walletService, times(1)).saveWallet(receiver);

        verify(notificationService, times(1)).senderNotification(sender, "Transação realizada com sucesso");
        verify(notificationService, times(1)).senderNotification(receiver, "Transação recebida com sucesso");
    }
}