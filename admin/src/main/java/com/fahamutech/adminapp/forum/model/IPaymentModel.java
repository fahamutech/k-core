package com.fahamutech.adminapp.forum.model;

public interface IPaymentModel {
    String getAmount();

    void setAmount(String amount);

    String getMessage();

    void setMessage(String message);

    String getNumber();

    void setNumber(String number);

    String getTime();

    void setTime(String time);

    String getUserId();

    void setUserId(String userId);

    boolean isFlag();

    void setFlag(boolean flag);
}
