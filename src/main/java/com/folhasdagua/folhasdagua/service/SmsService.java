package com.folhasdagua.folhasdagua.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    public SmsService(){
        sentCount = 0;
    }
    public final String ACCOUNT_SID = "AC4722bfa5c6861f955539adc46143ac1f";
    public final String AUTH_TOKEN = "e8e636fd7539538c5a04e46f51088cec";

    @Getter
    @Setter
    private int sentCount;

    public void send(String message) {
        if(sentCount == 0){
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message notification = Message.creator(
                            new com.twilio.type.PhoneNumber("+5544998724121"),
                            "MG9522a7c8a71c3c2695260eb296986ce5", message
                    )
                    .create();
            setSentCount(getSentCount() + 1);
        }
    }
}
