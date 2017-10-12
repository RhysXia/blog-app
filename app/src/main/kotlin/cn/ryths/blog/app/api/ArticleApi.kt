package cn.ryths.blog.app.api

import cn.ryths.blog.app.entity.Article
import cn.ryths.blog.app.entity.Result
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleApi {

    /**
     * 获取推荐文章
     */
    @GET("/articles/recommendation")
    fun findAllRecommendation(@Query("currentPage") currentPage: Int,
                              @Query("pageSize") pageSize: Int,
                              @Query("includeCategory") includeCategory: Boolean,
                              @Query("includeAuthor") includeAuthor: Boolean): Observable<Result<List<Article>>>

    /**
     * 获取文章列表
     */
    @GET("/articles")
    fun findAll(@Query("currentPage") currentPage: Int,
                @Query("pageSize") pageSize: Int,
                @Query("includeCategory") includeCategory: Boolean,
                @Query("includeAuthor") includeAuthor: Boolean): Observable<Result<List<Article>>>

    /**
     * 获取指定id文章
     */
    @GET("/articles/{id}")
    fun findById(@Path("id") id: Long,
                 @Query("includeCategory") includeCategory: Boolean,
                 @Query("includeAuthor") includeAuthor: Boolean,
                 @Query("includeContent") includeContent: Boolean): Observable<Result<Article>>

    /**
     * 为文章点赞
     */
    @PUT("/articles/{id}/praise")
    fun praise(@Path("id") id: Long): Observable<Result<Void?>>

}