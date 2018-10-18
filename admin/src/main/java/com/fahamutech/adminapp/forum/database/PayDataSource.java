package com.fahamutech.adminapp.forum.database;

import com.fahamutech.adminapp.forum.model.IPaymentModel;

public interface PayDataSource {
    void pay(IPaymentModel paymentModel);
}
