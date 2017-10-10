package cn.ryths.blog.app.entity

import java.util.*

/**
 *  write down description here
 *  <P>Company: www.ryths.cn</p>
 *  @author : xrs44
 *  @version :1.0
 *  @since  : 2017-09-05 17:19
 */

data class Reply(
        var content: String? = null,
        var createDate: Date? = null,
        var from: User? = null,
        var to: User? = null,
        var comment: Comment? = null
)