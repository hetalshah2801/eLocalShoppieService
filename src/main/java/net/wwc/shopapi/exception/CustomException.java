package net.wwc.shopapi.exception;


import net.wwc.shopapi.enums.ResultEnum;

/**
 * Created By Zhu Lin on 3/10/2018.
 */
public class CustomException extends RuntimeException {

    private Integer code;
    private String message;

    public CustomException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public CustomException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public CustomException(String message) {
        super(message);
        this.message = message;
    }
}
