package cn.ryths.blog.app.api

import cn.ryths.blog.app.entity.Article
import cn.ryths.blog.app.entity.Result
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleApi {

    @GET("/articles/recommendation")
    fun findAllRecommendation(@Query("currentPage") currentPage: Int,
                              @Query("pageSize") pageSize: Int,
                              @Query("includeCategory") includeCategory: Boolean,
                              @Query("includeAuthor") includeAuthor: Boolean): Observable<Result<List<Article>>>

    @GET("/articles")
    fun findAll(@Query("currentPage") currentPage: Int,
                @Query("pageSize") pageSize: Int,
                @Query("includeCategory") includeCategory: Boolean,
                @Query("includeAuthor") includeAuthor: Boolean): Observable<Result<List<Article>>>

    @GET("/articles/{id}")
    fun findById(@Path("id") id: Long,
                 @Query("includeCategory") includeCategory: Boolean,
                 @Query("includeAuthor") includeAuthor: Boolean,
                 @Query("includeContent") includeContent: Boolean): Observable<Result<Article>>

}