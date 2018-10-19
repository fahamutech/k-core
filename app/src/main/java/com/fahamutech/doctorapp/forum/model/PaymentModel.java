package com.fahamutech.doctorapp.forum.model;

public class PaymentModel implements IPaymentModel {
    private String message;
    private String number;
    private long time;
    private String userId;
    private boolean flag;
    private String amount;

    public PaymentModel() {

    }

    public PaymentModel(String amount, String message, String number, long time, String userId, boolean flag) {
        this.message = message;
        this.number = number;
        this.time = time;
        this.userId = userId;
        this.flag = flag;
        this.amount = amount;
    }

    @Override
    public String getAmount() {
        return amount;
    }

    @Override
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
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
