package com.fahamutech.doctorapp.forum.database;

import android.content.Context;

import com.fahamutech.doctorapp.forum.model.IPaymentModel;

public class PaymentNoSqlDataBase extends NoSqlDatabase implements PayDataSource {

    public PaymentNoSqlDataBase(Context context) {
        super(context);
    }

    @Override
    public void pay(IPaymentModel paymentModel) {

    }
}
