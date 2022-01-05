package com.fahamutech.kcore.admin.model

import java.io.Serializable

class Category(
    var name: String?,
    var description: String?,
    var image: String?,
    var id: String? = null
) : Serializable