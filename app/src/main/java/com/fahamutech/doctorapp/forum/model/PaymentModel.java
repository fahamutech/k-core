package com.fahamutech.doctorapp.forum.model;

public class PaymentModel implements IPaymentModel{
    private String amount;
    private String message;
    private String number;
    private String time;
    private String userId;
    private boolean flag;

    public PaymentModel(){

    }

    public PaymentModel(String amount, String message, String number, String time, String userId, boolean flag) {
        this.amount = amount;
        this.message = message;
        this.number = number;
        this.time = time;
        this.userId = userId;
        this.flag = flag;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
