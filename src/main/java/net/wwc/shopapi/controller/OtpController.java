package net.wwc.shopapi.controller;


import net.wwc.shopapi.model.SendOtpResponse;
import net.wwc.shopapi.service.impl.SendOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/otp")

public class OtpController {

    String senderId= "CSSHWE";
    String  authKey="330738AZV6IghfvsSE5ed200a8P1";
    String messageTemplate="Your otp is {{otp}}. Please do not share it with anybody";

    @Autowired
    SendOtpService sendOtpService;

    @PostMapping("/sendOtp")
    public SendOtpResponse sendOtp(@RequestParam String phoneNumber)
    {
      System.out.println("In sendOtp method");
      sendOtpService.setAuthKey(authKey);
      sendOtpService.setMessageTemplate(messageTemplate);
      SendOtpResponse sendOtpResponse= sendOtpService.send(phoneNumber,senderId);
      return sendOtpResponse;
    }

    @PostMapping("/verifyOtp")
    public SendOtpResponse verifyOtp( @RequestParam String phoneNumber, @RequestParam String otp)
    {
        System.out.println("In verifyOtp method");
        sendOtpService.setAuthKey(authKey);
        sendOtpService.setMessageTemplate(messageTemplate);
        SendOtpResponse verifyOtpResponse= sendOtpService.verify(phoneNumber,otp);
        return verifyOtpResponse;
    }

    @PostMapping("/resendOtp/{retryVoice}")
    public SendOtpResponse retryOtp( @RequestParam String phoneNumber,@PathVariable boolean retryVoice)
    {
        System.out.println("In retryOtp method");
        sendOtpService.setAuthKey(authKey);
        sendOtpService.setMessageTemplate(messageTemplate);
        SendOtpResponse retryOtp= sendOtpService.retry(phoneNumber,retryVoice);
        return retryOtp;
    }
}


