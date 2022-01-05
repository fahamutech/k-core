package com.fahamutech.kcore.user.model

import java.io.Serializable

class Testimony : Serializable {
    var id: String? = null
    var image: String? = null
    var date: String? = null

    constructor() {}
    constructor(id: String?, image: String?, date: String?) {
        this.id = id
        this.image = image
        this.date = date
    }
}