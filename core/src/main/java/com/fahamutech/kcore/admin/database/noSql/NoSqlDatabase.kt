package com.fahamutech.kcore.admin.database.noSql

import android.content.Context
import com.fahamutech.kcore.utils.getKCoreFirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

abstract class NoSqlDatabase internal constructor(context: Context) {
    @JvmField
    var firestore: FirebaseFirestore
    @JvmField
    protected var context: Context

    init {
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .setSslEnabled(true)
            .build()
        firestore = FirebaseFirestore.getInstance(getKCoreFirebaseApp(context))
        firestore.firestoreSettings = settings
        this.context = context
    }
}