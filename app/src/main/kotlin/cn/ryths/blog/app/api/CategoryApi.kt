package cn.ryths.blog.app.api

import cn.ryths.blog.app.entity.Article
import cn.ryths.blog.app.entity.Category
import cn.ryths.blog.app.entity.Result
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryApi {

    @GET("/categories")
    fun findAll(@Query("currentPage") currentPage: Int,
                @Query("pageSize") pageSize: Int): Observable<Result<List<Category>>>

    @GET("categories")
    fun count(): Observable<Result<Long>>

}