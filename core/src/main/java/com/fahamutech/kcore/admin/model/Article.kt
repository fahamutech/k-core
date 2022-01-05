package com.fahamutech.kcore.admin.model

import java.io.Serializable

class Article(
    var categoryId: String?,
    var date: String?,
    var content: String?,
    var packageType: String?,
    var image: String?,
    var title: String?
) : Serializable {
    var id: String? = null

}