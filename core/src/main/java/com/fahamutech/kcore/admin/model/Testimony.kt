package com.fahamutech.kcore.admin.model

import java.io.Serializable

class Testimony(override var image: String?, override var date: String?) : Serializable,
    ITestimony {
    override var id: String? = null

}