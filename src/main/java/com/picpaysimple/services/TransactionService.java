package com.picpaysimple.services;

import com.picpaysimple.dtos.TransactionDTO;
import com.picpaysimple.entities.transaction.Transaction;
import com.picpaysimple.entities.wallet.Wallet;
import com.picpaysimple.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private WalletService walletService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private NotificationService notificationService;

    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {
        Wallet sender = this.walletService.findWalletById(transactionDTO.senderId());
        Wallet receiver = this.walletService.findWalletById(transactionDTO.receiverId());

        walletService.validateTransaction(sender, transactionDTO.value());

        boolean isAuthorized = this.authorizationService.authorizeTransaction(sender, transactionDTO.value());
        if (!isAuthorized) {
            throw new Exception("Transação não autorizada");
        }

        Transaction newtransaction = new Transaction();
        newtransaction.setAmount(transactionDTO.value());
        newtransaction.setSender(sender);
        newtransaction.setReceiver(receiver);
        newtransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionDTO.value()));
        receiver.setBalance(receiver.getBalance().add(transactionDTO.value()));

        this.transactionRepository.save(newtransaction);
        this.walletService.saveWallet(sender);
        this.walletService.saveWallet(receiver);

        this.notificationService.senderNotification(sender, "Transação realizada com sucesso");
        this.notificationService.senderNotification(receiver, "Transação recebida com sucesso");

        return newtransaction;
    }
}
