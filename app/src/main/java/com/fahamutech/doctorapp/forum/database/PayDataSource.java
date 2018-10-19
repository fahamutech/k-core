package com.fahamutech.doctorapp.forum.database;

import com.fahamutech.doctorapp.forum.model.IPaymentModel;

public interface PayDataSource {
    void pay(IPaymentModel paymentModel, DataBaseCallback... dataBaseCallbacks);
}
