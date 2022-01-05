package com.fahamutech.kcore.user.model

import java.io.Serializable

class Article : Serializable {
    var categoryId: String? = null
    var id: String? = null
    var date: String? = null
    var content: String? = null
    var packageType: String? = null
    var image: String? = null
    var title: String? = null

    constructor() {}
    constructor(
        categoryId: String?, id: String?, date: String?, content: String?, packageType: String?,
        image: String?, title: String?
    ) {
        this.categoryId = categoryId
        this.id = id
        this.date = date
        this.content = content
        this.packageType = packageType
        this.image = image
        this.title = title
    }
}