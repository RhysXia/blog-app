package cn.ryths.blog.app.api

import cn.ryths.blog.app.entity.Article
import cn.ryths.blog.app.entity.Result
import io.reactivex.Observable
import retrofit2.http.*

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
     * 获取指定分类下文章列表
     */
    @GET("/categories/{categoryId}/articles")
    fun findAllByCategoryId(@Path("categoryId") categoryId: Long,
                            @Query("currentPage") currentPage: Int,
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
    @POST("/articles/{id}/praise")
    fun praise(@Path("id") id: Long): Observable<Result<Void?>>

    /**
     * 检查文章是否被点赞
     */
    @GET("/articles/{id}/isPraise")
    fun checkPraise(@Path("id") Id: Long): Observable<Result<Boolean>>

    /**
     * 取消点赞
     */
    @DELETE("/articles/{id}/praise")
    fun deletePraise(@Path("id") id: Long): Observable<Result<Void?>>

}