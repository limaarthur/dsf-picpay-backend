package com.picpaysimple.services;

import com.picpaysimple.dtos.TransactionDTO;
import com.picpaysimple.entities.transaction.Transaction;
import com.picpaysimple.entities.wallet.Wallet;
import com.picpaysimple.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private WalletService walletService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {
        Wallet sender = this.walletService.findWalletById(transactionDTO.senderId());
        Wallet receiver = this.walletService.findWalletById(transactionDTO.receiverId());

        walletService.validateTransaction(sender, transactionDTO.value());

        boolean isAuthorized = this.authorizeTransaction(sender, transactionDTO.value());
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

    public boolean authorizeTransaction(Wallet sender, BigDecimal value) {
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/6d4e7e3e-9718-4512-9758-60cc20cd967f", Map.class);

        if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
            String message = (String) authorizationResponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        } else return false;
    }
}
