package com.fahamutech.kcore.user.model

import java.io.Serializable

class Category : Serializable {
    var id: String? = null
    var name: String? = null
    var description: String? = null
    var image: String? = null

    constructor() {}
    constructor(id: String?, name: String?) {
        this.id = id
        this.name = name
    }
}