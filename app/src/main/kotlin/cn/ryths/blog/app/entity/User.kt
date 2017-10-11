package cn.ryths.blog.app.entity

/**
 *  write down description here
 *  <P>Company: www.ryths.cn</p>
 *  @author : xrs44
 *  @version :1.0
 *  @since  : 2017-09-05 17:19
 */
data class User(
        var id: Long? = null,
        var username: String? = null,
        var nickname: String? = null,
        var password: String? = null,
        var avatar: String? = null,
        var summary: String? = null,
        var roles: List<Role>? = null
)