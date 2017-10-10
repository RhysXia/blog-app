package cn.ryths.blog.app.api

import cn.ryths.blog.app.entity.Article
import cn.ryths.blog.app.entity.Result
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApi {

    @GET("/articles/recommendation")
    fun getRecommendation(@Query("currentPage") currentPage: Int,
                          @Query("pageSize") pageSize: Int,
                          @Query("includeCategory") includeCategory: Boolean = true): Observable<Result<List<Article>>>

    @GET("/articles")
    fun getList(@Query("currentPage") currentPage: Int,
                @Query("pageSize") pageSize: Int,
                @Query("includeCategory") includeCategory: Boolean = true): Observable<Result<List<Article>>>

}