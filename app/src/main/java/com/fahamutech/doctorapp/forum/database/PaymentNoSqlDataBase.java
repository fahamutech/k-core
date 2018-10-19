package com.fahamutech.doctorapp.forum.database;

import android.content.Context;

import com.fahamutech.doctorapp.forum.model.IPaymentModel;

public class PaymentNoSqlDataBase extends NoSqlDatabase implements PayDataSource {

    public PaymentNoSqlDataBase(Context context) {
        super(context);
    }

    @Override
    public void pay(IPaymentModel paymentModel, DataBaseCallback... dataBaseCallbacks) {
        firestore.collection(ForumC.PAYMENT.name()).document()
                .set(paymentModel)
                .addOnSuccessListener(aVoid -> dataBaseCallbacks[0].then("done"))
                .addOnFailureListener(e -> dataBaseCallbacks[1].then("fail : " + e));
    }
}
