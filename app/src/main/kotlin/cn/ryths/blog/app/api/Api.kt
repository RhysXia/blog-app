package cn.ryths.blog.app.api

import android.util.Log
import cn.ryths.blog.app.BaseApplication
import cn.ryths.blog.app.utils.TokenUtils
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Api {

    private val builder = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(
                    GsonConverterFactory.create(
                            GsonBuilder()
                                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                                    .create()
                    )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    /**
     * 创建api实例
     */
    fun <T> newApiInstance(service: Class<T>): T {
        val context = BaseApplication.getContext()
        if (context != null) {
            val okhttp = OkHttpClient.Builder()
                    .addInterceptor {
                        var token = TokenUtils.getToken(context)
                        if (token == null){
                            token = ""
                        }
                        val request = it.request()
                                .newBuilder()
                                .addHeader("Authorization", token)
                                .build()
                        Log.d("http request","url:${request.url().uri().toASCIIString()}")
                        it.proceed(request)

                    }
                    .build()
            builder.client(okhttp)
        }
        return builder.build().create(service)
    }
}