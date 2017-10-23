package cn.ryths.blog.app.service

import cn.ryths.blog.app.api.Api
import cn.ryths.blog.app.api.ArticleApi
import cn.ryths.blog.app.entity.Article
import cn.ryths.blog.app.entity.Code
import cn.ryths.blog.app.entity.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 文章数据提供类
 */
class ArticleService {


    /**
     * api访问接口
     */
    private val articleApi = Api.newApiInstance(ArticleApi::class.java)


    /**
     * 获取文章列表
     */
    fun findAll(currentPage: Int, pageSize: Int, callback: ServiceCallback<Result<List<Article>>, String?>) {
        articleApi.findAll(currentPage, pageSize, true, true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    //判断请求是否成功
                    if (it.code == Code.SUCCESS) {
                        //成功回调
                        callback.success(it)
                    } else {
                        //失败回调
                        callback.fail(it.message)
                    }
                }, {
                    callback.fail(it.message)
                })
    }

    /**
     * 获取推荐文章列表
     */
    fun findAllRecommendation(currentPage: Int, pageSize: Int, callback: ServiceCallback<Result<List<Article>>, String?>) {
        articleApi.findAllRecommendation(currentPage, pageSize, false, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    //判断请求是否成功
                    if (it.code == Code.SUCCESS) {
                        //成功回调
                        callback.success(it)
                    } else {
                        //失败回调
                        callback.fail(it.message)
                    }
                },{
                    callback.fail(it.message)
                })
    }

    /**
     * 获取指定文章
     */
    fun findById(id: Long, callback: ServiceCallback<Article?, String?>) {
        articleApi.findById(id, true, true, true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    if (it.code == Code.SUCCESS) {
                        callback.success(it.data)
                    } else {
                        callback.fail(it.message)
                    }
                },{
                    callback.fail(it.message)
                })
    }

    /**
     * 点赞
     */
    fun praise(id: Long, callback: ServiceCallback<Void, String?>) {
        articleApi.praise(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    //判断请求是否成功
                    if (it.code == Code.SUCCESS) {
                        //成功回调
                        callback.success(Void.TYPE.newInstance())
                    } else {
                        //失败回调
                        callback.fail(it.message)
                    }
                },{
                    callback.fail(it.message)
                })

    }

    /**
     * 检测文章是否被点赞了
     */
    fun isPraise(id:Long,callback: ServiceCallback<Boolean, String?>){
        articleApi.checkPraise(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    //判断请求是否成功
                    if (it.code == Code.SUCCESS) {
                        //成功回调
                        callback.success(it.data!!)
                    } else {
                        //失败回调
                        callback.fail(it.message)
                    }
                },{
                    callback.fail(it.message)
                })
    }


}