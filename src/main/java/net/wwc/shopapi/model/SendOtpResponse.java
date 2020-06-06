package net.wwc.shopapi.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import net.wwc.shopapi.util.JsonUtil;

/**
 *
 * 
 */

@Getter
@Setter
public class SendOtpResponse extends Response {
  private String otp;
  private String phoneNumber;

  public String getOtp()
  {
    return this.otp;
  }

  public SendOtpResponse(String otp, String message, String type,String phoneNumber) {
    super(message, type);
    this.otp = otp;
    this.phoneNumber=phoneNumber;
  }


  
  public String asString() {
    try {
      return JsonUtil.toJsonAsString(this);
    } catch (JsonProcessingException ex) {
      return null;
    }
  }
}
