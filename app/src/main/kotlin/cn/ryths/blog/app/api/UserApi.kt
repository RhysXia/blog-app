package cn.ryths.blog.app.api

import cn.ryths.blog.app.entity.Result
import cn.ryths.blog.app.entity.User
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApi {

    /**
     * 登录
     */
    @POST("/token")
    fun login(@Body user: User): Observable<Result<String>>

    @PUT("/token")
    fun register(@Body user:User):Observable<Result<User>>
}