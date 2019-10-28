package com.bill.customer;

import com.bill.customer.config.security.AppConfig;
import com.bill.customer.dto.EmailMessage;
import com.bill.customer.exception.CustomerPortalException;
import com.bill.customer.service.EmailService;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetResponse;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class EmailServiceTest {

    @InjectMocks
    EmailService emailService;
    @Mock
    MailjetClient emailClient;
    @Mock
    AppConfig appConfig;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();


    @Test
    public void sendEmailTest() throws Exception{
        EmailMessage emailMessage = new EmailMessage("neerajbhattneeraj@gmail.com", "First Email", "Welcom email");
        Mockito.when(emailClient.post(ArgumentMatchers.any())).thenReturn(new MailjetResponse(200, new JSONObject()));
        Mockito.when(appConfig.getApplicationName()).thenReturn("customer-portal");
        Mockito.when(appConfig.getMailjetSenderEmail()).thenReturn("xyz@abc.com");


        emailService.send(emailMessage);
    }

    @Test
    public void errorOnSendingEmail() throws Exception{

        expectedEx.expect(RuntimeException.class);
        expectedEx.expectMessage("Error on sending email");

        EmailMessage emailMessage = new EmailMessage("neerajbhattneeraj@gmail.com", "First Email", "Welcom email");
        Mockito.when(emailClient.post(ArgumentMatchers.any())).thenThrow(new RuntimeException("Error on sending email"));
        Mockito.when(appConfig.getApplicationName()).thenReturn("customer-portal");
        Mockito.when(appConfig.getMailjetSenderEmail()).thenReturn("xyz@abc.com");


        emailService.send(emailMessage);
    }

    @Test
    public void errorCodeReturnOnSendEmail() throws Exception{

        expectedEx.expect(CustomerPortalException.class);
        expectedEx.expectMessage("No able to send the email");

        EmailMessage emailMessage = new EmailMessage("neerajbhattneeraj@gmail.com", "First Email", "Welcom email");
        Mockito.when(emailClient.post(ArgumentMatchers.any())).thenReturn(new MailjetResponse(401, new JSONObject()));
        Mockito.when(appConfig.getApplicationName()).thenReturn("customer-portal");
        Mockito.when(appConfig.getMailjetSenderEmail()).thenReturn("xyz@abc.com");


        emailService.send(emailMessage);
    }

}
