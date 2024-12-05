package com.picpaysimple.services;

import com.picpaysimple.dtos.NotificationDTO;
import com.picpaysimple.entities.wallet.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    public void senderNotification(Wallet wallet, String message) throws Exception {
        String email = wallet.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);

        //ResponseEntity<String> notificationResponse = restTemplate.postForEntity("", notificationRequest, String.class);

        //if (!(notificationResponse.getStatusCode() == HttpStatus.OK)) {
            //throw new Exception("Serviço de notificação está fora do ar");
        //}

        System.out.println("Notificação enviada para o usuário");
    }
}
