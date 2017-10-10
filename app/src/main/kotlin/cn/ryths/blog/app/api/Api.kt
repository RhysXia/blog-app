package cn.ryths.blog.app.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    private val restrofit = Retrofit.Builder()
            .baseUrl("http://10.6.12.209:8080")
            .addConverterFactory(
                    GsonConverterFactory.create(
                            GsonBuilder()
                                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                                    .create()
                    )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    /**
     * 创建api实例
     */
    fun <T> newApiInstance(service: Class<T>): T {
        return restrofit.create(service)
    }
}