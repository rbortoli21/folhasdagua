package com.folhasdagua.folhasdagua.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    public final String ACCOUNT_SID = "AC4722bfa5c6861f955539adc46143ac1f";
    public final String AUTH_TOKEN = "91bb07bb6c13a326182bf6c216875d39";

    public void send(String message) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message notification = Message.creator(
                        new com.twilio.type.PhoneNumber("+5544998724121"),
                        "MG9522a7c8a71c3c2695260eb296986ce5", message
                        )
                .create();

        System.out.println(notification.getSid());
    }
}
