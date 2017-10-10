package cn.ryths.blog.app.entity

import java.util.*

/**
 *  write down description here
 *  <P>Company: www.ryths.cn</p>
 *  @author : xrs44
 *  @version :1.0
 *  @since  : 2017-09-05 17:19
 */

data class Comment(
        var id: Long? = null,
        var content: String? = null,
        var createDate: Date? = null,
        var author: User? = null,
        var replies: List<Reply>? = null,
        var article: Article? = null
)