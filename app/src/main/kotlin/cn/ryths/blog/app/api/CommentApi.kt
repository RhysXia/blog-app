package cn.ryths.blog.app.api

import cn.ryths.blog.app.entity.Comment
import cn.ryths.blog.app.entity.Result
import io.reactivex.Observable
import retrofit2.http.*

interface CommentApi {

    /**
     * 获取推荐文章
     */
    @GET("/articles/{articleId}/comments")
    fun findAllComments(
            @Path("articleId") articleId: Long,
            @Query("currentPage") currentPage: Int,
            @Query("pageSize") pageSize: Int,
            @Query("includeArticle") includeArticle: Boolean,
            @Query("includeAuthor") includeAuthor: Boolean,
            @Query("includeReply") includeReply: Boolean,
            @Query("orderBy") orderBy:Array<String> = arrayOf("createDate:DESC")): Observable<Result<List<Comment>>>

    @POST("/comments")
    fun addComment(@Body commentDto: CommentDto):Observable<Result<Comment>>
}

data class CommentDto(
        var id: Long? = null,
        var content: String? = null,
        var articleId: Long? = null
)
