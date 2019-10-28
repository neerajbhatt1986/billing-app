package com.bill.customer.service;

import com.bill.customer.config.security.AppConfig;
import com.bill.customer.dto.EmailMessage;
import com.bill.customer.exception.CustomerPortalException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private MailjetClient emailClient;
    @Autowired
    private AppConfig appConfig;

    public void send(EmailMessage message){
        MailjetRequest mailjetRequest = getMailjetRequest(message);
        MailjetResponse response=null;
        try{
            response = emailClient.post(mailjetRequest);
        }catch (Exception e){
            throw new RuntimeException("Error on sending email ");
        }
        if(response.getStatus() != HttpStatus.OK.value()){
            logger.error(response.getData().toString());
            throw new CustomerPortalException("No able to send the email");
        }
    }

    private MailjetRequest getMailjetRequest(EmailMessage message){
        return new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Name", appConfig.getApplicationName())
                                        .put("Email", appConfig.getMailjetSenderEmail()))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", message.getEmail())
                                        ))
                                .put(Emailv31.Message.SUBJECT, message.getSubject())
                                .put(Emailv31.Message.HTMLPART, message.getMessage())));
    }
}
