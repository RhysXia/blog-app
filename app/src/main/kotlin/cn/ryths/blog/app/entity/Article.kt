package cn.ryths.blog.app.entity

import java.util.*

data class Article(
        var id: Long? = null,
        var title: String? = null,
        var content: String? = null,
        var summary: String? = null,
        //文章大图
        var poster: String? = null,
        var createDate: Date? = null,
        var updateDate: Date? = null,
        //阅读数
        var readNum: Long? = null,
        //赞数
        var praiseNum: Long? = null,
        var author: User? = null,
        var comments: List<Comment>? = null,
        var category: Category? = null
)